package com.sknau.choosecheese

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LoginApiClient {
    private const val baseUrl = "http://ec2-15-165-176-80.ap-northeast-2.compute.amazonaws.com:8000/"
    fun getClient(accessToken: String): Retrofit {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(accessToken))
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}