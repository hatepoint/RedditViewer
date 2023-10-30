package com.hatepoint.redditviewer.ui.screens.feed

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.koin.androidx.compose.koinViewModel

@Composable
fun FeedScreen(
    viewModel: FeedViewModel = koinViewModel(),
) {
    when (val state = viewModel.uiState.collectAsState().value) {
        is FeedState.Loading -> {
            Text(text = "Loading...")
        }
        is FeedState.Success -> {
            val posts = viewModel.convertPostsToPublications(state.data)
            LazyColumn {
                items(posts) {
                    PostCard(
                        publication = it,
                    )
                }
            }
        }
        is FeedState.Error -> {
            Text(text = "Error: ${state.error}")
        }
    }

}


