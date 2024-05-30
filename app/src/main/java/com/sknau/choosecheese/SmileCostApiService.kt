package com.sknau.choosecheese

import retrofit2.Call
import retrofit2.http.GET

interface SmileCostApiService {
    @GET("main/misopoint")
    fun getImages(): Call<SmileCostData>
}