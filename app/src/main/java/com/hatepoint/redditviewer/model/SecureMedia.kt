package com.hatepoint.redditviewer.model

import kotlinx.serialization.Serializable

@Serializable
data class SecureMedia(
    val reddit_video: RedditVideo
)