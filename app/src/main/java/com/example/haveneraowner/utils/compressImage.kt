package com.example.haveneraowner.utils
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream

fun compressImage(context: Context, uri: Uri): File {
    val bitmap = if (android.os.Build.VERSION.SDK_INT < 28) {
        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    } else {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        ImageDecoder.decodeBitmap(source)
    }

    val file = File(context.cacheDir, "compressed_${System.currentTimeMillis()}.jpg")

    val outputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, outputStream)
    outputStream.flush()
    outputStream.close()

    return file
}
