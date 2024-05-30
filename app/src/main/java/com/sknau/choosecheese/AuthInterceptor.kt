package com.sknau.choosecheese

import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(private val accessToken: String) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response{
        val request = chain.request()
        val builder = request.newBuilder()
            .addHeader("Authorization","Bearer $accessToken" )

        return chain.proceed(builder.build())
    }
}