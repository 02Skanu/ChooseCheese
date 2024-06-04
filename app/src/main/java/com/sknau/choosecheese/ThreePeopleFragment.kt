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

class ThreePeopleFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PeopleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_threepeople, container, false)
        recyclerView = view.findViewById(R.id.people3_recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = this.getActivity()?.getSharedPreferences("accessTOKEN",
            AppCompatActivity.MODE_PRIVATE
        )
        val authToken = sharedPreferences?.getString("accessToken", null) ?: ""

        val retrofit = LogicApiClient.getClient(authToken)
        val apiService = retrofit.create(People3ApiService::class.java)

        apiService.getImages().enqueue(object : Callback<PeopleResponse> {
            override fun onResponse(call: Call<PeopleResponse>, response: Response<PeopleResponse>) {
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

            override fun onFailure(call: Call<PeopleResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun sendHeartClick(imageUrl: String, authToken: String) {
        val retrofit = LogicApiClient.getClient(authToken)
        val apiService = retrofit.create(People3ApiService::class.java)

        apiService.sendImageClick(imageUrl).enqueue(object : Callback<ClickResponseData> {
            override fun onResponse(call: Call<ClickResponseData>, response: Response<ClickResponseData>) {
                if (response.isSuccessful) {
                    Log.d("PeopleFragment", "Image click sent successfully")
                } else {
                    Log.e("PeopleFragment", "Failed to send image click")
                }
            }

            override fun onFailure(call: Call<ClickResponseData>, t: Throwable) {
                Log.e("PeopleFragment", "Failed to send image click", t)
            }
        })
    }
}
