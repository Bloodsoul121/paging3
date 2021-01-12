package com.blood.jetpackpaging3.paging.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.paging.*
import androidx.room.withTransaction
import com.blood.jetpackpaging3.paging.bean.DataX
import com.blood.jetpackpaging3.paging.db.AppDatabase
import com.blood.jetpackpaging3.paging.net.NetApi

class PagingViewModel : ViewModel() {

    companion object {
        const val PAGE_SIZE = 20
    }

    val netApi = NetApi.create()

    @OptIn(ExperimentalPagingApi::class)
    fun getPager() = Pager(
        PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = 5,
            enablePlaceholders = false,
            initialLoadSize = PAGE_SIZE
        ),
        initialKey = 0,
        pagingSourceFactory = {
            object : PagingSource<Int, DataX>() {
                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataX> {
                    Log.d("bloodsoul", "PagingSource load thread : ${Thread.currentThread().name}")
                    return try {
                        val pagerNum = params.key ?: 0
                        val result = netApi.load(pagerNum)
                        val prevKey = if (pagerNum <= 0) null else pagerNum - 1
                        val expectNextKey = pagerNum + 1
                        val nextKey = if (expectNextKey > 3) null else expectNextKey
                        Log.d("bloodsoul", "PagingSource load: $pagerNum ${result.data.datas.size}")
                        LoadResult.Page(
                            result.data.datas,
                            prevKey,
                            nextKey
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                        LoadResult.Error(e)
                    }
                }
            }
        })
        .flow
//        .cachedIn(viewModelScope)
//        .asLiveData(viewModelScope.coroutineContext)


    /**
     *

    D/blood: log: refresh NotLoading
    D/blood: log: append NotLoading


    D/OkHttp: --> GET https://www.wanandroid.com/article/list/0/json
    D/blood: remoteMediator load: REFRESH androidx.paging.PagingState@6361f85
    D/blood: log: refresh Loading
    D/blood: log: refresh Loading
    D/OkHttp: <-- 200 OK https://www.wanandroid.com/article/list/0/json
    D/blood: remoteMediator load: PREPEND androidx.paging.PagingState@c4a3e971
    D/blood: log: refresh Loading
    D/blood: log: refresh NotLoading


    D/OkHttp: --> GET https://www.wanandroid.com/article/list/1/json
    D/blood: log: append NotLoading
    D/OkHttp: <-- 200 OK https://www.wanandroid.com/article/list/1/json (537ms, unknown-length body)
    D/blood: log: append NotLoading

     *
     */

    private val database = AppDatabase.getInstance()
    private val articleDao = AppDatabase.getInstance().articleDao

    @OptIn(ExperimentalPagingApi::class)
    fun getPagerByRemote() = Pager(
        PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = 5,
            enablePlaceholders = false,
            initialLoadSize = PAGE_SIZE
        ),
        initialKey = 0,
        remoteMediator = object : RemoteMediator<Int, DataX>() {
            override suspend fun load(
                loadType: LoadType,
                state: PagingState<Int, DataX>
            ): MediatorResult {
                return try {

                    Log.d("bloodsoul", "remote loadType: $loadType")

                    val lastData = state.lastItemOrNull()

                    val pageKey = when (loadType) {
                        LoadType.REFRESH -> {
                            null
                        }
                        LoadType.PREPEND -> {
                            return MediatorResult.Success(endOfPaginationReached = true)
                        }
                        LoadType.APPEND -> {
                            if (lastData == null) {
                                return MediatorResult.Success(endOfPaginationReached = true)
                            }
                            lastData.page + 1
                        }
                    }

                    Log.d("bloodsoul", "remote pageKey: $pageKey")

                    val page = pageKey ?: 0
                    val result = netApi.load(page)
                    val articles = result.data.datas
                    val endOfPaginationReached = articles.isEmpty()

                    val items = articles.map {
                        it.page = page
                        it
                    }

                    Log.d("bloodsoul", "remote map: ${items.lastOrNull()}")

                    database.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            articleDao.deleteArticles()
                        }
                        articleDao.insertArticles(items)
                    }

                    MediatorResult.Success(endOfPaginationReached)

                } catch (e: Exception) {
                    e.printStackTrace()
                    MediatorResult.Error(e)
                }
            }
        },
        pagingSourceFactory = {
            Log.d("bloodsoul", "PagingSource load")
            articleDao.queryArticles()
        }
    ).flow

}