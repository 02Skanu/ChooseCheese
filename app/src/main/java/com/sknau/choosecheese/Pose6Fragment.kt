package com.sknau.choosecheese

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Pose6Fragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PoseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_pose6, container, false)
        recyclerView = view.findViewById(R.id.pose6_recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context,2)
        return view
    }
    //  페이징
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = this.getActivity()?.getSharedPreferences("accessTOKEN",
            AppCompatActivity.MODE_PRIVATE
        )
        val authToken = sharedPreferences?.getString("accessToken", null)?.let {
            it
        } ?: ""
        val retrofit = LogicApiClient.getClient(authToken)
        val apiService = retrofit.create(Pose6ApiService::class.java)

        apiService.getImages().enqueue(object : Callback<PoseResponseData> {
            override fun onResponse(call: Call<PoseResponseData>, response: Response<PoseResponseData>) {
                if (response.isSuccessful) {
                    val imageUrls = response.body()?.images ?: emptyList()
                    val poseDataList = imageUrls.map { PoseData(it) }
                    Log.d("check", "List contents: $poseDataList")

                    adapter = PoseAdapter(poseDataList)
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<PoseResponseData>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}