package com.blood.jetpackpaging3.paging.net

import com.blood.jetpackpaging3.paging.bean.ArticleResult
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


/*
 *  @项目名：  JetpackPaging3 
 *  @包名：    com.blood.jetpackpaging3.paging
 *  @文件名:   NetApi
 *  @创建者:   bloodsoul
 *  @创建时间:  2021/1/3 17:03
 *  @描述：    TODO
 */
interface NetApi {

    companion object {

        private const val BASE_URL = "https://www.wanandroid.com/"

        fun create(): NetApi {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetApi::class.java)
        }
    }

    @GET("article/list/{pagerNum}/json")
    suspend fun load(@Path("pagerNum") pagerNum: Int): ArticleResult

}