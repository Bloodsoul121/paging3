package com.blood.jetpackpaging3.paging

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*


/*
 *  @项目名：  JetpackPaging3 
 *  @包名：    com.blood.jetpackpaging3.paging
 *  @文件名:   PagingViewModel
 *  @创建者:   bloodsoul
 *  @创建时间:  2021/1/3 17:02
 *  @描述：    TODO
 */
class PagingViewModel : ViewModel() {

    val netApi = NetApi.create()

    @OptIn(ExperimentalPagingApi::class)
    val articleList = Pager(
        PagingConfig(
            pageSize = 30,
            prefetchDistance = 5,
            enablePlaceholders = true,
            initialLoadSize = 30
        ),
        initialKey = 0,
        remoteMediator = object : RemoteMediator<Int, DataX>() {
            override suspend fun load(loadType: LoadType, state: PagingState<Int, DataX>): MediatorResult {
                Log.d("Paging3", "remoteMediator load: $loadType $state")
                return MediatorResult.Success(false)
            }
        },
        pagingSourceFactory = {
            object : PagingSource<Int, DataX>() {
                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataX> {
                    return try {
                        val pagerNum = params.key ?: 0
                        val result = netApi.load(pagerNum)
                        val prevKey = if (pagerNum <= 0) null else pagerNum - 1
                        val expectNextKey = pagerNum + 1
                        val nextKey = if (expectNextKey > 3) null else expectNextKey
                        Log.d("Paging3", "PagingSource load: $pagerNum")
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
        .cachedIn(viewModelScope)
        .asLiveData(viewModelScope.coroutineContext)

}