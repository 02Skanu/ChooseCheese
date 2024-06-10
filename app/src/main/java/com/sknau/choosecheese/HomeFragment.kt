package com.sknau.choosecheese

import HomeImageAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var viewPager: RecyclerView
    private lateinit var storyPagerAdapter: StoryPagerAdapter
    private val storyUrlList: MutableList<String> = mutableListOf()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HomeImageAdapter
    private val imageUrlList: MutableList<String> = mutableListOf()
    private lateinit var handler: Handler
    private lateinit var updateRunnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedPreferences = this.activity?.getSharedPreferences(
            "accessTOKEN",
            AppCompatActivity.MODE_PRIVATE
        )
        val authToken = sharedPreferences?.getString("accessToken", null) ?: ""

        val retrofit = LogicApiClient.getClient(authToken)
        val apiService = retrofit.create(QrApiService::class.java)

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        viewPager = view.findViewById(R.id.main_story_recyclerview)
        viewPager.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // StoryPagerAdapter 초기화
        storyPagerAdapter = StoryPagerAdapter(storyUrlList, requireContext(), apiService)
        viewPager.adapter = storyPagerAdapter

        recyclerView = view.findViewById(R.id.home_recyclerview)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        // adapter 초기화
        adapter = HomeImageAdapter { imageUrl ->
            apiService.sendImageClick(ResponseData(imageUrl)).enqueue(object : Callback<ClickResponseData> {
                override fun onResponse(call: Call<ClickResponseData>, response: Response<ClickResponseData>) {
                    if (response.isSuccessful) {
                        val intent = Intent(activity, SmileCostActivity::class.java)
                        intent.putExtra("clickResponseData", response.body())
                        startActivity(intent)
                    } else {
                        Log.e("HomeFragment", "Failed to send image: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<ClickResponseData>, t: Throwable) {
                    Log.e("HomeFragment", "Failed to send image click", t)
                }
            })
        }

        recyclerView.adapter = adapter

        loadStoryImages(authToken)
        loadExistingImages(authToken)

        return view
    }

    private fun loadStoryImages(authToken: String) {
        val retrofit = LogicApiClient.getClient(authToken)
        val apiService = retrofit.create(QrApiService::class.java)

        apiService.getStoryImages().enqueue(object : Callback<ImageResponse> {
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                if (response.isSuccessful) {
                    val imageResponse = response.body()
                    if (imageResponse != null) {
                        val storyUrls = imageResponse.pdb_paths
                        storyUrlList.clear()
                        storyUrlList.addAll(storyUrls)
                        storyPagerAdapter.notifyDataSetChanged()
                        Log.d("HomeFragment", "스토리 이미지 로드 성공: $storyUrls")
                    } else {
                        Log.e("HomeFragment", "스토리 이미지 로드 실패: 응답 데이터 없음")
                    }
                } else {
                    Log.e("HomeFragment", "스토리 이미지 로드 실패: 응답 실패")
                }
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                Log.e("HomeFragment", "스토리 이미지 로드 실패", t)
            }
        })
    }

    private fun loadExistingImages(authToken: String) {
        val retrofit = LogicApiClient.getClient(authToken)
        val apiService = retrofit.create(QrApiService::class.java)

        apiService.getExistingImages().enqueue(object : Callback<ImageResponse> {
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                if (response.isSuccessful) {
                    val imageResponse = response.body()
                    if (imageResponse != null) {
                        val imageUrls = imageResponse.imageUrl
                        imageUrlList.clear()
                        imageUrlList.addAll(imageUrls)
                        adapter.setImages(imageUrls)
                        Log.d("HomeFragment", "이미지 로드 성공: $imageUrls")
                    } else {
                        Log.e("HomeFragment", "이미지 로드 실패: 응답 데이터 없음")
                    }
                } else {
                    Log.e("HomeFragment", "이미지 로드 실패: 응답 실패")
                }
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
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

        handler = Handler()
        updateRunnable = object : Runnable {
            override fun run() {
                val authToken = requireActivity().getSharedPreferences("accessTOKEN", AppCompatActivity.MODE_PRIVATE)
                    ?.getString("accessToken", null) ?: ""
                loadStoryImages(authToken)
                loadExistingImages(authToken)

                handler.postDelayed(this, 5000)
            }
        }

        handler.post(updateRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateRunnable)
    }
}
