package com.hatepoint.redditviewer.retrofit

import com.hatepoint.redditviewer.model.Reddit
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {
    @GET("top.json")
    suspend fun getTopPosts(
        @Query("after") after: String,
        @Query("limit") limit: Int,
        @Query("raw_json") rawJson: Int = 1
    ): Response<Reddit>
}