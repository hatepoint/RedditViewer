package com.hatepoint.redditviewer.model

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val after: String,
    val children: List<Children>,
    val dist: Int,
    val geo_filter: String,
    val modhash: String
)