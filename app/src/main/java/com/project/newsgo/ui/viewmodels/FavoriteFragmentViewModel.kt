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
class FavoriteFragmentViewModel@Inject constructor(
    private var firebaseFirestoreRepository: FirebaseFirestoreRepository,
)  :ViewModel(){

    val news = MutableLiveData<FirebaseFirestoreResult>()

    init {
        getFavorites()
    }

    fun getFavorites() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = firebaseFirestoreRepository.getFavorites()
            news.value = result
        }
    }
}