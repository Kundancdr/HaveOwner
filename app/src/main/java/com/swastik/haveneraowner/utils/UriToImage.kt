package com.swastik.haveneraowner.utils

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody


//fun UriToImage(context: Context, uri: Uri): MultipartBody.Part? {
//    return try {
//        val inputStream = context.contentResolver.openInputStream(uri)
//        val file = File.createTempFile("upload", ".jpg", context.cacheDir)
//        inputStream?.use { input ->
//            FileOutputStream(file).use { output ->
//                input.copyTo(output)
//            }
//        }
//        val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
//        MultipartBody.Part.createFormData("image", file.name, requestBody)
//    } catch (e: Exception) {
//        e.printStackTrace()
//        null
//    }
//}
fun UriToImage(context: Context, uri: Uri, fieldName: String = "images"): MultipartBody.Part? {
    return try {
        val compressedFile = compressImage(context, uri)
        val requestFile = compressedFile.asRequestBody("image/*".toMediaTypeOrNull())
        MultipartBody.Part.createFormData(fieldName, compressedFile.name, requestFile)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
