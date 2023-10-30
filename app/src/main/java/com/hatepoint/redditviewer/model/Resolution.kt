package com.hatepoint.redditviewer.model

import kotlinx.serialization.Serializable

@Serializable
data class Resolution(
    val height: Int,
    val url: String,
    val width: Int
)