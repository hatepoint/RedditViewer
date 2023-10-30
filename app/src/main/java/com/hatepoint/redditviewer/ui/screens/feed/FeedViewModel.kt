package com.hatepoint.redditviewer.ui.screens.feed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hatepoint.redditviewer.data.RedditRepository
import com.hatepoint.redditviewer.model.DataX
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.Date

class FeedViewModel(private val repository: RedditRepository): ViewModel() {
    private val _uiState = MutableStateFlow<FeedState>(FeedState.Loading)
    val uiState: StateFlow<FeedState> = _uiState

    init {
        Log.d("FeedViewModel", "init")
        viewModelScope.launch {
            getTopPosts("", 20).collect {
                _uiState.value = it
            }
        }
    }

    fun getTopPosts(after: String, limit: Int): Flow<FeedState> = flow {
        try{
            viewModelScope.launch {
                _uiState.value = FeedState.Loading
                val response = repository.getTopPosts(after, limit)
                if (response.isSuccessful) {
                    Log.d("FeedViewModel", "Success: ${response.body()!!.data.children}")
                    val posts = mutableListOf<DataX>()
                    response.body()!!.data.children.forEach { post ->
                        posts.add(post.data)
                    }
                    _uiState.value = FeedState.Success(posts)
                } else {
                    Log.d("FeedViewModel", "Error: ${response.message()}")
                    _uiState.value = FeedState.Error(response.message())
                }
            }
        } catch (e: Exception) {
            Log.d("FeedViewModel", "Error: ${e.message}")
            _uiState.value = FeedState.Error(e.message.toString())
        }
    }

    fun convertPostsToPublications(posts: List<DataX>): List<Publication> {
        val publications = mutableListOf<Publication>()
        posts.forEach { post ->
            publications.add(
                Publication(
                    author = post.author,
                    date = Date(post.created.toInt() * 1000L),
                    commentsCount = post.num_comments,
                    imageUrl = post.preview?.images?.get(0)?.resolutions?.last()?.url ?: post.thumbnail,
                    postContent = post.title
                )
            )
        }
        return publications
    }
}

sealed class FeedState() {
    data object Loading : FeedState()
    data class Success(val data: List<DataX>) : FeedState()
    data class Error(val error: String) : FeedState()
}