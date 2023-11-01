package com.hatepoint.redditviewer.data

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.bumptech.glide.Glide
import com.hatepoint.redditviewer.retrofit.RedditApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class RedditRepository(private val context: Context, val api: RedditApi) {

    suspend fun getTopPosts(after: String, limit: Int) =
        api.getTopPosts(after, limit)


    suspend fun saveImageToGallery(url: String, filename: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val bitmap = Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .submit()
                    .get()

                saveBitmapToGallery(context, bitmap, filename)
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    private fun saveBitmapToGallery(context: Context, bitmap: Bitmap, filename: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val imageCollection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "$filename.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }

            return try {
                val imageUri = context.contentResolver.insert(imageCollection, contentValues)
                imageUri?.let { uri ->
                    context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                        if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {
                            throw Exception("Failed to save bitmap")
                        }
                        true
                    } ?: false
                } ?: false
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, "$filename.jpg")

            return try {
                val outputStream = FileOutputStream(image)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()

                context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(image)))

                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

    }
}