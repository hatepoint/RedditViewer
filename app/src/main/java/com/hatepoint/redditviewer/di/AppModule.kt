package com.hatepoint.redditviewer.di

import com.hatepoint.redditviewer.retrofit.RetrofitClient
import com.hatepoint.redditviewer.data.RedditRepository
import com.hatepoint.redditviewer.ui.screens.feed.FeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { RetrofitClient.retrofitClient }
    single { RedditRepository(get(), get()) }
    viewModel { FeedViewModel(get()) }
}