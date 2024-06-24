package com.sknau.choosecheese

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object LogicApiClient {
    //private const val baseUrl = "https://u1dw5dgm39.execute-api.ap-northeast-2.amazonaws.com/test/"
    private const val baseUrl = "https://kxnibn0si9.execute-api.ap-northeast-2.amazonaws.com/prod/"
    fun getClient(accessToken: String): Retrofit {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor(accessToken))
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}