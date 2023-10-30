package com.hatepoint.redditviewer.model

import kotlinx.serialization.Serializable

@Serializable
data class Nsfw(
    val resolutions: List<Resolution>,
    val source: Source
)