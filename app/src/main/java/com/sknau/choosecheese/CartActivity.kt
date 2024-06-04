package com.sknau.choosecheese

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PeopleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = findViewById(R.id.cart_recyclerview)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        fetchLikedImages()
    }

    private fun fetchLikedImages() {
        val sharedPreferences = getSharedPreferences("accessTOKEN", MODE_PRIVATE)
        val authToken = sharedPreferences.getString("accessToken", null) ?: ""

        val retrofit = LogicApiClient.getClient(authToken)
        val apiService = retrofit.create(People1ApiService::class.java)

        apiService.getLikedImages().enqueue(object : Callback<PeopleResponse> {
            override fun onResponse(call: Call<PeopleResponse>, response: Response<PeopleResponse>) {
                if (response.isSuccessful) {
                    val imageUrls = response.body()?.images ?: emptyList()
                    val peopleDataList = imageUrls.map { PeopleData(it) }
                    adapter = PeopleAdapter(peopleDataList) { imageUrl ->
                    }
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<PeopleResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
