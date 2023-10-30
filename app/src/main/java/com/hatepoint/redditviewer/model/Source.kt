package com.hatepoint.redditviewer.model

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val height: Int,
    val url: String,
    val width: Int
)