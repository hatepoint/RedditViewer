package com.hatepoint.redditviewer.model

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class DataX(
    val author: String,
    val created: Double,
    val created_utc: Double,
    val id: String,
    val is_original_content: Boolean,
    val is_reddit_media_domain: Boolean,
    val is_robot_indexable: Boolean,
    val is_self: Boolean,
    val is_video: Boolean,
    val media_embed: MediaEmbed,
    val media_only: Boolean,
    val name: String,
    val no_follow: Boolean,
    val num_comments: Int,
    val num_crossposts: Int,
    val over_18: Boolean,
    val permalink: String,
    val pinned: Boolean,
    val preview: Preview? = null,
    val quarantine: Boolean,
    val saved: Boolean,
    val score: Int,
    val thumbnail: String,
    val thumbnail_height: Int,
    val thumbnail_width: Int,
    val title: String,
    val url: String,
    val url_overridden_by_dest: String,
)