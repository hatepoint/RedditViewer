package com.hatepoint.redditviewer.data

import com.hatepoint.redditviewer.retrofit.RedditApi

class RedditRepository(private val api: RedditApi) {

    suspend fun getTopPosts(after: String, limit: Int) =
        api.getTopPosts(after, limit)


}