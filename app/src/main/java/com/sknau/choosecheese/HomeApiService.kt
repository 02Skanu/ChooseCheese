package com.sknau.choosecheese

import retrofit2.Call
import retrofit2.http.GET

interface HomeApiService {
    @GET("main")
    fun getImage(): Call<ResponseData>
}