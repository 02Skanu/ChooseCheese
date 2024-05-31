package com.sknau.choosecheese

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LogicApiClient {
    //private const val baseUrl = "https://u1dw5dgm39.execute-api.ap-northeast-2.amazonaws.com/test/"
    private const val baseUrl = "https://kxnibn0si9.execute-api.ap-northeast-2.amazonaws.com/prod/"

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