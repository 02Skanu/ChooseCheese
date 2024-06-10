package com.sknau.choosecheese

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {

    @POST("login")
    fun userLogin(
        @Body jsonParmas : LoginData,
    ): Call<LoginBackendResponse>

    companion object{
        private const val BASE_URL = "http://ec2-15-165-176-80.ap-northeast-2.compute.amazonaws.com:8000/"
        val gson : Gson = GsonBuilder().setLenient().create();

        fun create() : LoginApiService{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(LoginApiService::class.java)
        }
    }
}