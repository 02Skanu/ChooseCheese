package com.sknau.choosecheese

import retrofit2.Call
import retrofit2.http.GET

interface RankApiService {
    @GET("main/misoranking")
    fun getRanks(): Call<RankData>
}
