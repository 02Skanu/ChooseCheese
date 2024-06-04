package com.sknau.choosecheese

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RankFragment : Fragment() {

    private lateinit var rankRecyclerView: RecyclerView
    private lateinit var totalScoreTextView: TextView
    private lateinit var rankAdapter: RankAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rank, container, false)

        rankRecyclerView = view.findViewById(R.id.rank_recyclerview)
        totalScoreTextView = view.findViewById(R.id.rank_total_score)

        rankRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        rankAdapter = RankAdapter(emptyList())
        rankRecyclerView.adapter = rankAdapter

        sharedPreferences = requireActivity().getSharedPreferences("accessTOKEN", AppCompatActivity.MODE_PRIVATE)

        fetchRanks()

        return view
    }

    private fun fetchRanks() {
        val authToken = sharedPreferences.getString("accessToken", null) ?: ""

        val apiService = LogicApiClient.getClient(authToken).create(RankApiService::class.java)
        val call = apiService.getRanks()

        call.enqueue(object : Callback<RankData> {
            override fun onResponse(call: Call<RankData>, response: Response<RankData>) {
                if (response.isSuccessful) {
                    val rankData = response.body()
                    rankData?.let {
                        rankAdapter.updateRanks(it.rank)
                        totalScoreTextView.text = "최종 미소 점수: ${it.total_score}"
                    }
                }
            }

            override fun onFailure(call: Call<RankData>, t: Throwable) {
                // Handle error
            }
        })
    }
}
