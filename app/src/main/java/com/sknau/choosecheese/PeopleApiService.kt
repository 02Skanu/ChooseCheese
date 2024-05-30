package com.sknau.choosecheese

import retrofit2.Call
import retrofit2.http.GET

interface People1ApiService {
    @GET("recommend/posebyperson/1")
    fun getImages(): Call<PeopleResponse>
}

interface People2ApiService {
    @GET("recommend/posebyperson/2")
    fun getImages(): Call<PeopleResponse>
}
interface People3ApiService {
    @GET("recommend/posebyperson/3")
    fun getImages(): Call<PeopleResponse>
}
interface People4ApiService {
    @GET("recommend/posebyperson/4")
    fun getImages(): Call<PeopleResponse>
}
interface People5ApiService {
    @GET("recommend/posebyperson/5")
    fun getImages(): Call<PeopleResponse>
}