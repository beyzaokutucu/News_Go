package com.project.newsgo.retrofit

class ApiUtils {
    companion object{
        private const val baseUrl="https://newsapi.org/v2/"

        fun getNewsDao():NewsDao{
            return RetrofitClient.getClient(baseUrl).create(NewsDao::class.java)
        }
    }
}