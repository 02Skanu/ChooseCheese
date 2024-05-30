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
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST


class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageView: ImageView
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
        val authToken = sharedPreferences?.getString("accessToken", null)?.let {
            it
        } ?: ""
        val testData = QrData(token = authToken)
        val retrofit = LogicApiClient.getClient(authToken)
        val apiService = retrofit.create(QrApiService::class.java)

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.home_recyclerview) // RecyclerView를 참조
        imageView = view.findViewById(R.id.main_test)

        // 가로로 2열, 세로로 무한 스크롤 설정
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        // 어댑터 초기화 및 RecyclerView에 연결



        adapter = HomeImageAdapter()
        recyclerView.adapter = adapter


        apiService.sendData(testData).enqueue(object : Callback<List<ResponseData>> {
            override fun onResponse(call: Call<List<ResponseData>>, response: Response<List<ResponseData>>) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    responseData?.forEach { ResponseData ->
                        val imageUrlList = ResponseData.imageUrl
                        imageUrlList?.forEach { imageUrl ->
                            Log.d("HomeFragment", "Token: $authToken")
                            Log.d("HomeFragment", "Image URL: $imageUrl")
                            Glide.with(this@HomeFragment)
                                .load(imageUrl)
                                .into(imageView)
                        }
                    }
                } else {
                    Log.e("HomeFragment", "Failed to load image")
                }
            }

            override fun onFailure(call: Call<List<ResponseData>>, t: Throwable) {
                Log.e("HomeFragment", "Failed to load image", t)
            }
        })




        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<ImageButton>(R.id.main_cheese_logo)
        button.setOnClickListener {
            val intent = Intent(activity, RecommendActivity::class.java)
            startActivity(intent)
        }

        //Qr에서 ImageUrl을 보내온 것을 safe args를 통해 String을 받음.
        val imageUrl = arguments?.getString("imageUrl")
        imageUrl?.split(",")?.forEach { url ->
            imageUrlList.add(url)
            adapter.notifyItemInserted(imageUrlList.size - 1)

        }
    }
}
