package com.sknau.choosecheese

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface QrApiService {
    @POST("crawling_server")
    //@POST("qrcode")
    fun sendData(@Body data: QrData): Call<Void>

    @POST("crawling_server")
    //@POST("qrcode")
    fun sendImageClick(@Body imageUrl: String): Call<ClickResponseData>

    @GET("crawling_server")
    //@GET("qrcode")
    fun getExistingImages(): Call<List<ResponseData>>
}