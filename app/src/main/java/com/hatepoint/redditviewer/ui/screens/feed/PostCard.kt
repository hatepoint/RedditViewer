package com.hatepoint.redditviewer.ui.screens.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import java.util.Calendar
import java.util.Date

data class Publication(
    val author: String,
    val date: Date,
    val commentsCount: Int,
    val imageUrl: String?,
    val postContent: String
)

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PostCard(publication: Publication) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = publication.author,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            val hoursAgo = remember {
                val currentTime = Calendar.getInstance().time
                val diff = currentTime.time - publication.date.time
                val hours = diff / (60 * 60 * 1000)
                "$hours hours ago"
            }
            Text(
                text = hoursAgo,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        publication.imageUrl?.let {
            val density = LocalDensity.current.density
            val imageSize = (150 * density).toInt()
            val imageModifier = Modifier
                .width(imageSize.dp)
                .padding(top = 16.dp, bottom = 16.dp)
                .defaultMinSize(minWidth = imageSize.dp / 2, minHeight = imageSize.dp / 2)
                .clickable { uriHandler.openUri(publication.imageUrl) }
            GlideImage(
                model = it,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = imageModifier
            )
        }
        Text(
            text = publication.postContent,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Comments: ${publication.commentsCount}",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PublicationCardPreview() {
    val samplePublication = Publication(
        author = "John Doe",
        date = Date(),
        commentsCount = 5,
        imageUrl = "https://commons.wikimedia.org/wiki/File:Wikipedia-logo-v2.svg",
        postContent = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    )
    PostCard(publication = samplePublication)
}