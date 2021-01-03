package com.blood.jetpackpaging3.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn


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

    val articleList = Pager(PagingConfig(pageSize = 30, prefetchDistance = 5), initialKey = 0) {
        object : PagingSource<Int, DataX>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataX> {
                val pagerNum = params.key ?: 0
                return try {
                    val result = netApi.load(pagerNum)
                    LoadResult.Page(result.data.datas, null, pagerNum + 1)
                } catch (e: Exception) {
                    e.printStackTrace()
                    LoadResult.Error(e)
                }
            }
        }
    }
        .flow
        .cachedIn(viewModelScope)
        .asLiveData(viewModelScope.coroutineContext)

}