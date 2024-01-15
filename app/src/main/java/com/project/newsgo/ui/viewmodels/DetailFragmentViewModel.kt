package com.project.newsgo.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.newsgo.data.entity.Article
import com.project.newsgo.data.repository.FirebaseFirestoreRepository
import com.project.newsgo.data.repository.NewsRepository
import com.project.newsgo.firebase.FirebaseFirestoreResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel @Inject constructor(
    private var firebaseFirestoreRepository: FirebaseFirestoreRepository,
) : ViewModel() {
    val isFavorite = MutableLiveData<FirebaseFirestoreResult>()

    fun checkFavorited(article: Article) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = firebaseFirestoreRepository.checkFavorited(article)
            isFavorite.value = result
        }
    }

    fun insertFavorite(
        article: Article,
    ) {
        CoroutineScope(Dispatchers.Main).launch {

            firebaseFirestoreRepository.insertFavorite(article)
            checkFavorited(article)

        }
    }

    fun deleteFavorite(article: Article) {
        CoroutineScope(Dispatchers.Main).launch {
            firebaseFirestoreRepository.deleteFavorite(article)
            checkFavorited(article)

        }
    }


}