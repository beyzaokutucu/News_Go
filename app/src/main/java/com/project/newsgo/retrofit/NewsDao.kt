package com.project.newsgo.retrofit

import com.project.newsgo.data.entity.News
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsDao {

    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String,
    ): News

}