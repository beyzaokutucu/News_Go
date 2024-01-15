package com.project.newsgo.data.repository

import com.project.newsgo.data.datasource.FirebaseFirestoreDataSource
import com.project.newsgo.data.entity.Article

class FirebaseFirestoreRepository (private val firebaseFirestoreDataSource: FirebaseFirestoreDataSource){

    suspend fun getFavorites() = firebaseFirestoreDataSource.getFavorites()
    suspend fun checkFavorited(article: Article) = firebaseFirestoreDataSource.checkFavorited(article)
    suspend fun deleteFavorite(article: Article) = firebaseFirestoreDataSource.deleteFavorite(article)
    suspend fun insertFavorite(article: Article) = firebaseFirestoreDataSource.insertFavorite(article)
}