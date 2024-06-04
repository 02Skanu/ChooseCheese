package com.sknau.choosecheese

import HomeImageAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HomeImageAdapter
    private val imageUrlList: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedPreferences = this.getActivity()?.getSharedPreferences("accessTOKEN",
            AppCompatActivity.MODE_PRIVATE
        )
        val authToken = sharedPreferences?.getString("accessToken", null) ?: ""

        val retrofit = LogicApiClient.getClient(authToken)
        val apiService = retrofit.create(QrApiService::class.java)

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.home_recyclerview)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        adapter = HomeImageAdapter { imageUrl ->
            apiService.sendImageClick(imageUrl).enqueue(object : Callback<ClickResponseData> {
                override fun onResponse(call: Call<ClickResponseData>, response: Response<ClickResponseData>) {
                    if (response.isSuccessful) {
                        val intent = Intent(activity, SmileCostActivity::class.java)
                        intent.putExtra("clickResponseData", response.body())
                        startActivity(intent)
                    } else {
                        Log.e("HomeFragment", "Failed to send image")
                    }
                }

                override fun onFailure(call: Call<ClickResponseData>, t: Throwable) {
                    Log.e("HomeFragment", "Failed to send image click", t)
                }
            })
        }
        recyclerView.adapter = adapter
        loadExistingImages(authToken)


        return view
    }

    private fun loadExistingImages(authToken: String) {
        val retrofit = LogicApiClient.getClient(authToken)
        val apiService = retrofit.create(QrApiService::class.java)

        apiService.getExistingImages().enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful) {
                    val imageUrls = response.body()?.imageUrl ?: emptyList()

                    imageUrlList.addAll(imageUrls)
                    adapter.addImages(imageUrls)

                    Log.d("HomeFragment", "이미지 로드 성공: $imageUrls")
                } else {
                    Log.e("HomeFragment", "XXXXXX")
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.e("HomeFragment", "이미지 로드 실패", t)
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<ImageButton>(R.id.main_cheese_logo)
        button.setOnClickListener {
            val intent = Intent(activity, RecommendActivity::class.java)
            startActivity(intent)
        }
    }
}
