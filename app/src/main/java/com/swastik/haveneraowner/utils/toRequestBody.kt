package com.swastik.haveneraowner.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

fun String.toRequestBody(): RequestBody =
    RequestBody.create("text/plain".toMediaTypeOrNull(), this)
