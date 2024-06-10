package com.sknau.choosecheese

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Pose1Fragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PeopleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_pose1, container, false)
        recyclerView = view.findViewById(R.id.pose1_recyclerView)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = this.activity?.getSharedPreferences("accessTOKEN", AppCompatActivity.MODE_PRIVATE)
        val authToken = sharedPreferences?.getString("accessToken", null) ?: ""

        val retrofit = LogicApiClient.getClient(authToken)
        val apiService = retrofit.create(Pose1ApiService::class.java)

        apiService.getImages().enqueue(object : Callback<PoseResponseData> {
            override fun onResponse(call: Call<PoseResponseData>, response: Response<PoseResponseData>) {
                if (response.isSuccessful) {
                    val imageUrls = response.body()?.images ?: emptyList()
                    val peopleDataList = imageUrls.map { PeopleData(it) }
                    Log.d("check", "List contents: $peopleDataList")

                    adapter = PeopleAdapter(peopleDataList) { imageUrl ->
                        sendHeartClick(imageUrl, authToken)
                    }
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<PoseResponseData>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun sendHeartClick(imageUrl: String, authToken: String) {
        val retrofit = LogicApiClient.getClient(authToken)
        val apiService = retrofit.create(Pose1ApiService::class.java)

        apiService.sendImageClick(imageUrl).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("PeopleFragment", "이미지 클릭 성공")
                } else {
                    Log.e("PeopleFragment", "응~ 응답 안")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("PeopleFragment", "응~ 망함", t)
            }
        })
    }
}
