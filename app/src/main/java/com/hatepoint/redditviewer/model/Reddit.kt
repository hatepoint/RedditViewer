package com.hatepoint.redditviewer.model

import kotlinx.serialization.Serializable

@Serializable
data class Reddit(
    val `data`: Data,
    val kind: String
)