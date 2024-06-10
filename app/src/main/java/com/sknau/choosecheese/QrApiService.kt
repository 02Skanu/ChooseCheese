package com.sknau.choosecheese

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface QrApiService {
    @POST("crawling_server")
    //@POST("qrcode")
    fun sendData(@Body data: QrData): Call<Void>

    @GET("main")
    fun getExistingImages(): Call<ImageResponse>

    @POST("main/misopoint")
    fun sendImageClick(@Body data: ResponseData): Call<ClickResponseData>

    @GET("main")
    fun getStoryImages(): Call<ImageResponse>



    @POST("main/demo")
    fun sendTogetherImages(@Body data: sendTogetherData): Call<List<String>>

}


