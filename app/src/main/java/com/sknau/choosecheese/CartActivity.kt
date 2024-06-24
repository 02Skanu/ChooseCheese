package com.sknau.choosecheese

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val btn: ImageButton = findViewById(R.id.cart_back_button)
        btn.setOnClickListener {
            val intent = Intent(this, RecommendActivity::class.java)
            startActivity(intent)
        }


        recyclerView = findViewById(R.id.cart_recyclerview)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        fetchLikedImages()
    }

    private fun fetchLikedImages() {
        val sharedPreferences = getSharedPreferences("accessTOKEN", MODE_PRIVATE)
        val authToken = sharedPreferences.getString("accessToken", null) ?: ""

        val retrofit = LogicApiClient.getClient(authToken)
        val apiService = retrofit.create(People1ApiService::class.java)

        apiService.getLikedImages().enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    val imageUrls = response.body() ?: emptyList()
                    val peopleDataList = imageUrls.map { PeopleData(it) }
                    adapter = CartAdapter(peopleDataList)
                    recyclerView.adapter = adapter
                } else {
                    Log.e("Cart", "You Can't 접속")
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
