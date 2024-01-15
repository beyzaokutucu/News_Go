package com.project.newsgo.data.repository

import com.project.newsgo.data.datasource.NewsDataSource

class NewsRepository(var newsDataSource: NewsDataSource) {
    suspend fun getNews(query:String,page:Int) = newsDataSource.getNews(query,page)
}