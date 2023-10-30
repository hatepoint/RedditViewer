package com.hatepoint.redditviewer.model

import kotlinx.serialization.Serializable

@Serializable
data class Children(
    val `data`: DataX,
    val kind: String
)