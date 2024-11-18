package com.dev.trackerinv.ui.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Base64
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import java.io.ByteArrayOutputStream
import java.io.InputStream

class ImagePickerUtil(
    private val fragment: Fragment
) {

    // Launcher to pick image from gallery
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    // Initialize image picker with result handling
    fun initialize(onImagePicked: (Uri?) -> Unit) {
        galleryLauncher = fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val uri: Uri? = result.data?.data
                onImagePicked(uri)
            }
        }
    }

    // Open the gallery to pick an image
    fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryLauncher.launch(intent)
    }

    companion object {
        fun getFileNameFromUri(requireContext: Context, uri: Uri): String {
            var fileName = "Unknown"
            val cursor: Cursor? = requireContext.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    fileName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
            return fileName
        }

        // Function to convert URI to Bitmap
        fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
            return try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        // Function to compress Bitmap to ensure size is <= 2MB
        fun compressBitmapToMaxSize(bitmap: Bitmap): Bitmap {
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, true) // Resize
            val outputStream = ByteArrayOutputStream()
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream) // Compress further
            return BitmapFactory.decodeByteArray(outputStream.toByteArray(), 0, outputStream.size())
        }
        // Function to convert Bitmap to Base64 string
        fun convertBitmapToBase64(bitmap: Bitmap): String {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
            return Base64.encodeToString(byteArray, Base64.DEFAULT)
        }

        // Function to convert URI to Base64 String directly
        fun convertUriToBase64(context: Context, uri: Uri): String? {
            val bitmap = getBitmapFromUri(context, uri)?.let { compressBitmapToMaxSize(it) }
            return bitmap?.let { convertBitmapToBase64(it) }
        }
        fun base64ToBitmap(base64String: String): Bitmap? {
            return try {
                val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

    }
}
