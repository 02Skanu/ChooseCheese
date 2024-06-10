package com.sknau.choosecheese

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Pose1ApiService {
    @GET("recommend/posebytheme/1")
    fun getImages(): Call<PoseResponseData>

    @POST("recommend/cart")
    fun sendImageClick(@Body imageUrl: String): Call<Void>


}
interface Pose2ApiService {
    @GET("recommend/posebytheme/2")
    fun getImages(): Call<PoseResponseData>

    @POST("recommend/cart")
    fun sendImageClick(@Body imageUrl: String): Call<Void>
}
interface Pose3ApiService {
    @GET("recommend/posebytheme/3")
    fun getImages(): Call<PoseResponseData>

    @POST("recommend/cart")
    fun sendImageClick(@Body imageUrl: String): Call<Void>
}
interface Pose4ApiService {
    @GET("recommend/posebytheme/4")
    fun getImages(): Call<PoseResponseData>

    @POST("recommend/cart")
    fun sendImageClick(@Body imageUrl: String): Call<Void>
}
interface Pose5ApiService {
    @GET("recommend/posebytheme/5")
    fun getImages(): Call<PoseResponseData>

    @POST("recommend/cart")
    fun sendImageClick(@Body imageUrl: String): Call<Void>
}
interface Pose6ApiService {
    @GET("recommend/posebytheme/6")
    fun getImages(): Call<PoseResponseData>

    @POST("recommend/cart")
    fun sendImageClick(@Body imageUrl: String): Call<Void>
}