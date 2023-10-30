package com.hatepoint.redditviewer.model

import kotlinx.serialization.Serializable

@Serializable
data class Obfuscated(
    val resolutions: List<Resolution>,
    val source: Source
)