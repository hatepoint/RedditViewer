package com.hatepoint.redditviewer.model

import kotlinx.serialization.Serializable

@Serializable
data class Media(
    val reddit_video: RedditVideo
)