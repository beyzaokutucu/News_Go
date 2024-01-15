package com.project.newsgo.di

import com.google.firebase.firestore.FirebaseFirestore
import com.project.newsgo.data.datasource.FirebaseFirestoreDataSource
import com.project.newsgo.data.datasource.NewsDataSource
import com.project.newsgo.data.repository.FirebaseFirestoreRepository
import com.project.newsgo.data.repository.NewsRepository
import com.project.newsgo.retrofit.ApiUtils
import com.project.newsgo.retrofit.NewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideNewsRepository(newsDataSource: NewsDataSource): NewsRepository {
        return NewsRepository(newsDataSource)
    }

    @Provides
    @Singleton
    fun provideNewsDataSource(newsDao: NewsDao): NewsDataSource {
        return NewsDataSource(newsDao)
    }

    @Provides
    @Singleton
    fun provideNewsDao(): NewsDao {
        return ApiUtils.getNewsDao()
    }


    @Provides
    @Singleton
    fun provideFirebaseFirestoreRepository(firebaseFirestoreDataSource: FirebaseFirestoreDataSource): FirebaseFirestoreRepository {
        return FirebaseFirestoreRepository(firebaseFirestoreDataSource)
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestoreDataSource(firebaseFirestoreInstance: FirebaseFirestore): FirebaseFirestoreDataSource {
        return FirebaseFirestoreDataSource(firebaseFirestoreInstance)
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

}