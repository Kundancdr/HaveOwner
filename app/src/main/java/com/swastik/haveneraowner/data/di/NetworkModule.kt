package com.swastik.haveneraowner.data.di

import android.util.Log
import com.swastik.haveneraowner.data.ApiServices
import com.swastik.haveneraowner.data.DataStoreManager
import com.swastik.haveneraowner.data.TokenProvider
import com.swastik.haveneraowner.data.repo.RepoImpl
import com.swastik.haveneraowner.domain.Repo.Repo
import com.swastik.haveneraowner.utils.Constant.BASE_URL
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    // Provide DataStore<Preferences>
    single { DataStoreManager(get()) }

    // Provide TokenProvider
    single { TokenProvider(get()) }

    // Provide OkHttpClient with token handling
    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val tokenProvider: TokenProvider = get()
                val accessToken = tokenProvider.getAccessToken()
                Log.d("OkHttp", "Current AccessToken: $accessToken")

                val request = chain.request().newBuilder()
                    .apply {
                        if (!accessToken.isNullOrEmpty()) {
                            addHeader("Authorization", "Bearer $accessToken")
                        }
                    }
                    .build()

                Log.d("OkHttp", "Request URL: ${request.url}")
                Log.d("OkHttp", "Request Headers: ${request.headers}")

                val response = chain.proceed(request)
                Log.d("OkHttp", "Response Code: ${response.code}")
                Log.d("OkHttp", "Response Message: ${response.message}")
                if (!response.isSuccessful) {
                    Log.e("OkHttp", "Error Body: ${response.peekBody(2048).string()}")
                }

                response
            }
            .build()
    }

    // Provide Retrofit
    single {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Provide ApiServices
    single { get<Retrofit>().create(ApiServices::class.java) }

    // Provide Repo
    single<Repo> { RepoImpl(get(), get()) }
}