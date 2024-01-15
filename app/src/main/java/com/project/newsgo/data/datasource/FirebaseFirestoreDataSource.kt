package com.project.newsgo.data.datasource

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.project.newsgo.data.entity.Article
import com.project.newsgo.firebase.FirebaseFirestoreResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseFirestoreDataSource(private val firebaseFirestoreInstance: FirebaseFirestore) {

    companion object {
        var error = ""
    }

    suspend fun getFavorites(): FirebaseFirestoreResult =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val favoriteList: ArrayList<Article> = arrayListOf()
                val  querySnapshot =
                    firebaseFirestoreInstance.collection("favorites").get().await()
                for (doc in querySnapshot.documents){
                    val currentArticle= doc.data?.let { Article.fromMap(it) }
                    if (currentArticle != null) {
                        favoriteList.add(currentArticle)
                    }
                }
                FirebaseFirestoreResult.Success(favoriteList)
            } catch (exception: Exception) {
                Log.e("TAG",exception.message.toString())
                error = exception.localizedMessage
                    ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
                FirebaseFirestoreResult.Failure(error)
            }
        }

    suspend fun deleteFavorite(
        article: Article,
    ): FirebaseFirestoreResult = withContext(Dispatchers.IO) {
        return@withContext try {
            val author = article.author
            val title = article.title
            firebaseFirestoreInstance.collection("favorites").document("$author-$title").delete()
                .await()
            FirebaseFirestoreResult.Success(true)
        } catch (e: Exception) {
            error = e.localizedMessage
                ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
            FirebaseFirestoreResult.Failure(error)
        }
    }

    suspend fun insertFavorite(article: Article): FirebaseFirestoreResult =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val author = article.author
                val title = article.title
                firebaseFirestoreInstance.collection("favorites").document("$author-$title")
                    .set(article.toMap())
                    .await()
                FirebaseFirestoreResult.Success(true)
            } catch (exception: Exception) {
                error = exception.localizedMessage
                    ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
                Log.e("Error", error)
                FirebaseFirestoreResult.Failure(error)
            }
        }

    suspend fun checkFavorited(article: Article): FirebaseFirestoreResult =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val author = article.author
                val title = article.title
                val documentSnapshot =
                    firebaseFirestoreInstance.collection("favorites").document("$author-$title").get().await()
                FirebaseFirestoreResult.Success( documentSnapshot.exists())
            } catch (exception: Exception) {
                error = exception.localizedMessage
                    ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
                Log.e("Error", error)
                FirebaseFirestoreResult.Failure(error)
            }
        }

}