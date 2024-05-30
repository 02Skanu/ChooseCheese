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
import com.sknau.choosecheese.databinding.FragmentTwopeopleBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TwoPeopleFragment: Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PeopleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_twopeople, container, false)
        recyclerView = view.findViewById(R.id.people2_recyclerView)
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
        val apiService = retrofit.create(People2ApiService::class.java)

        apiService.getImages().enqueue(object : Callback<PeopleResponse> {
            override fun onResponse(call: Call<PeopleResponse>, response: Response<PeopleResponse>) {
                if (response.isSuccessful) {
                    val imageUrls = response.body()?.images ?: emptyList()
                    val peopleDataList = imageUrls.map { PeopleData(it) }
                    Log.d("check", "List contents: $peopleDataList")

                    adapter = PeopleAdapter(peopleDataList)
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<PeopleResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}