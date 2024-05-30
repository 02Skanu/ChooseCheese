package com.sknau.choosecheese

import retrofit2.Call
import retrofit2.http.GET

interface Pose1ApiService {
    @GET("recommend/posebyperson/1")
    fun getImages(): Call<PoseResponseData>
}
interface Pose2ApiService {
    @GET("recommend/posebytheme/2")
    fun getImages(): Call<PoseResponseData>
}
interface Pose3ApiService {
    @GET("recommend/posebytheme/3")
    fun getImages(): Call<PoseResponseData>
}
interface Pose4ApiService {
    @GET("recommend/posebytheme/4")
    fun getImages(): Call<PoseResponseData>
}
interface Pose5ApiService {
    @GET("recommend/posebytheme/5")
    fun getImages(): Call<PoseResponseData>
}
interface Pose6ApiService {
    @GET("recommend/posebytheme/6")
    fun getImages(): Call<PoseResponseData>
}