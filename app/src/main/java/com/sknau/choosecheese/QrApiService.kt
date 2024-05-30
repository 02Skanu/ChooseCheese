package com.sknau.choosecheese

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface QrApiService {
    @POST("/test/main/")
    fun sendData(@Body data: QrData): Call<List<ResponseData>>
}