package com.hatepoint.redditviewer.ui.screens.feed

import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    viewModel: FeedViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHost = remember { SnackbarHostState() }
    val snackbarText = viewModel.snackbarState.collectAsState()
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {}

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHost) }
    ) {
        Column(modifier = Modifier.padding(it)) {
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
                                onSaveImageClick = { url, name ->
                                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                        launcher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    }
                                    viewModel.saveImage(url, name)
                                }
                            )
                        }
                    }
                    if (snackbarText.value != "") {
                        LaunchedEffect(Unit) {
                            coroutineScope.launch {
                                snackbarHost.showSnackbar(snackbarText.value)
                            }
                        }

                    }
                }
                is FeedState.Error -> {
                    Text(text = "Error: ${state.error}")
                }
            }
        }

    }



}


