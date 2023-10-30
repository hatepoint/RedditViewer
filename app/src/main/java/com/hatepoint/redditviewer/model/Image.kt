package com.hatepoint.redditviewer.model

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val id: String,
    val resolutions: List<Resolution>,
    val source: Source,
)