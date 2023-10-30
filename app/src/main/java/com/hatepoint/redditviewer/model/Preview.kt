package com.hatepoint.redditviewer.model

import kotlinx.serialization.Serializable

@Serializable
data class Preview(
    val enabled: Boolean,
    val images: List<Image>,
)