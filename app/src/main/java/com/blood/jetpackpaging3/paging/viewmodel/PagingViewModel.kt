package com.blood.jetpackpaging3.paging.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.paging.*
import com.blood.jetpackpaging3.paging.bean.DataX
import com.blood.jetpackpaging3.paging.net.NetApi

class PagingViewModel : ViewModel() {

    companion object {
        const val PAGE_SIZE = 20
    }

    val netApi = NetApi.create()

    @OptIn(ExperimentalPagingApi::class)
    val articleList = Pager(
        PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = 5,
            enablePlaceholders = false,
            initialLoadSize = PAGE_SIZE
        ),
        initialKey = 0,
//        remoteMediator = object : RemoteMediator<Int, DataX>() {
//            override suspend fun load(
//                loadType: LoadType,
//                state: PagingState<Int, DataX>
//            ): MediatorResult {
//                Log.d(
//                    "blood",
//                    "remoteMediator load: $loadType ${state.anchorPosition} ${state.pages.size}"
//                )
//                return MediatorResult.Success(false)
//            }
//        },
        pagingSourceFactory = {
            object : PagingSource<Int, DataX>() {
                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataX> {
                    Log.d("blood", "PagingSource load thread : ${Thread.currentThread().name}")
                    return try {
                        val pagerNum = params.key ?: 0
                        val result = netApi.load(pagerNum)
                        val prevKey = if (pagerNum <= 0) null else pagerNum - 1
                        val expectNextKey = pagerNum + 1
                        val nextKey = if (expectNextKey > 3) null else expectNextKey
                        Log.d("blood", "PagingSource load: $pagerNum")
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

}