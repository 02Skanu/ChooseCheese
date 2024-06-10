package com.sknau.choosecheese

import PieChartApiService
import RecommendApiService
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.sknau.choosecheese.databinding.ActivityRecommendBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecommendActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecommendBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button: ImageButton = findViewById(R.id.recommend_back_button)

        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        setupUI()

        // Retrieve authToken from SharedPreferences
        val sharedPreferences = getSharedPreferences("accessTOKEN", MODE_PRIVATE)
        val authToken = sharedPreferences.getString("accessToken", null) ?: ""

        setupPieChart(authToken)
        setupPieChart2(authToken)
        setupViewPager(authToken)

    }

    private fun setupUI() {
        val image_p: ImageView = findViewById(R.id.recommend_image_p)
        val image_t: ImageView = findViewById(R.id.recommend_image_t)
        val image_c: ImageView = findViewById(R.id.recommend_image_c)
        image_p.bringToFront()
        image_t.bringToFront()
        image_c.bringToFront()
        val text_p: TextView = findViewById(R.id.recommend_text_p)
        val text_t: TextView = findViewById(R.id.recommend_text_t)
        val text_c: TextView = findViewById(R.id.recommend_text_c)
        text_p.bringToFront()
        text_t.bringToFront()
        text_c.bringToFront()

        val button_p: Button = findViewById(R.id.recommend_button_p)
        val button_t: Button = findViewById(R.id.recommend_button_t)
        val button_c: Button = findViewById(R.id.recommend_button_c)

        button_p.setOnClickListener {
            val intent = Intent(this, PeopleActivity::class.java)
            startActivity(intent)
        }
        button_t.setOnClickListener {
            val intent = Intent(this, PoseActivity::class.java)
            startActivity(intent)
        }
        button_c.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupPieChart(authToken: String) {
        binding.recommendPieChart1.setUsePercentValues(true)

        val retrofit = LogicApiClient.getClient(authToken)
            .create(PieChartApiService::class.java)

        retrofit.getChartData().enqueue(object : Callback<ChartData> {
            override fun onResponse(
                call: Call<ChartData>,
                response: Response<ChartData>
            ) {
                if (response.isSuccessful) {
                    val dataMap = response.body()

                    val poseStatusMap = dataMap?.pose_status

                    if (poseStatusMap != null) {
                        val dataList = poseStatusMap.map { (key, value) ->
                            try {
                                PieEntry(value.toFloat(), key)
                            } catch (e: NumberFormatException) {
                                Log.e("RecommendActivity", "Invalid float value: $value for key: $key")
                                null
                            }
                        }

                        val dataSet = PieDataSet(dataList, "").apply {
                            valueTextColor = Color.BLACK
                            valueTextSize = 10f
                            colors = listOf(
                                ContextCompat.getColor(this@RecommendActivity, R.color.pastel_1),
                                ContextCompat.getColor(this@RecommendActivity, R.color.pastel_2),
                                ContextCompat.getColor(this@RecommendActivity, R.color.pastel_3),
                                ContextCompat.getColor(this@RecommendActivity, R.color.pastel_4),
                                ContextCompat.getColor(this@RecommendActivity, R.color.pastel_5),
                                ContextCompat.getColor(this@RecommendActivity, R.color.pastel_6),
                                ContextCompat.getColor(this@RecommendActivity, R.color.pastel_7),
                                ContextCompat.getColor(this@RecommendActivity, R.color.pastel_8),
                                ContextCompat.getColor(this@RecommendActivity, R.color.pastel_9),
                                ContextCompat.getColor(this@RecommendActivity, R.color.pastel_10)
                            )
                        }

                        val pieData = PieData(dataSet)
                        binding.recommendPieChart1.apply {
                            data = pieData
                            setEntryLabelColor(Color.BLACK)
                            setEntryLabelTextSize(10F)
                            centerText = "포즈별"
                            invalidate()
                            animateY(1400, Easing.EaseInOutQuad)
                            legend.isEnabled = false
                        }
                    } else {
                        Log.d("pieCheck", "Response body is null")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.d("pieCheck", "Failed: ${response.code()} - $errorBody")
                }
            }

            override fun onFailure(call: Call<ChartData>, t: Throwable) {
                Log.d("pieCheck", "onFailure: ${t.message}")
            }
        })
    }
    private fun setupPieChart2(authToken: String) {
        binding.recommendPieChart2.setUsePercentValues(true)

        val retrofit = LogicApiClient.getClient(authToken)
            .create(PieChartApiService::class.java)

        retrofit.getChartData().enqueue(object : Callback<ChartData> {
            override fun onResponse(
                call: Call<ChartData>,
                response: Response<ChartData>
            ) {
                if (response.isSuccessful) {
                    val dataMap = response.body()

                    val moodStatusMap = dataMap?.mood_status

                    if (moodStatusMap != null) {
                        val dataList = moodStatusMap.map { (key, value) ->
                            try {
                                PieEntry(value.toFloat(), key)
                            } catch (e: NumberFormatException) {
                                Log.e("RecommendActivity", "Invalid float value: $value for key: $key")
                                null
                            }
                        }

                        val dataSet = PieDataSet(dataList, "").apply {
                            valueTextColor = Color.BLACK
                            valueTextSize = 10f
                            colors = listOf(
                                ContextCompat.getColor(this@RecommendActivity, R.color.pastel_1),
                                ContextCompat.getColor(this@RecommendActivity, R.color.pastel_2),
                                ContextCompat.getColor(this@RecommendActivity, R.color.pastel_3),
                                ContextCompat.getColor(this@RecommendActivity, R.color.pastel_4),
                                ContextCompat.getColor(this@RecommendActivity, R.color.pastel_5),
                                ContextCompat.getColor(this@RecommendActivity, R.color.pastel_6),
                                ContextCompat.getColor(this@RecommendActivity, R.color.pastel_7),
                                ContextCompat.getColor(this@RecommendActivity, R.color.pastel_8),
                                ContextCompat.getColor(this@RecommendActivity, R.color.pastel_9),
                                ContextCompat.getColor(this@RecommendActivity, R.color.pastel_10)
                            )
                        }

                        val pieData = PieData(dataSet)
                        binding.recommendPieChart2.apply {
                            data = pieData
                            setEntryLabelColor(Color.BLACK)
                            setEntryLabelTextSize(10F)
                            centerText = "테마별"
                            invalidate()
                            animateY(1400, Easing.EaseInOutQuad)
                            legend.isEnabled = false
                        }
                    } else {
                        Log.d("pieCheck2", "Response body is null")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.d("pieCheck2", "Failed: ${response.code()} - $errorBody")
                }
            }

            override fun onFailure(call: Call<ChartData>, t: Throwable) {
                Log.d("pieCheck2", "onFailure: ${t.message}")
            }
        })
    }
    private fun setupViewPager(authToken: String) {
        val viewPager: ViewPager2 = findViewById(R.id.recommend_viewpager)
        val retrofit2 = LogicApiClient.getClient(authToken).create(RecommendApiService::class.java)

        lifecycleScope.launch {
            try {
                val recommendations = retrofit2.getViewPagerData()
                val adapter = RecommendPagerAdapter(recommendations.recommend_images ?: emptyList())
                viewPager.apply {
                    clipToPadding = false
                    clipChildren = false
                    setPadding(50, 0, 50, 0)
                    setPageTransformer(MarginPageTransformer(20))
                }

                viewPager.adapter = adapter
            } catch (e: Exception) {
                Log.e("RecommendActivity", "Error getting recommendations: ${e.message}")
            }
        }
        viewPager.setCurrentItem(0, false)
        viewPager.addItemDecoration(MarginItemDecoration(16))
        viewPager.setPageTransformer(ScalePageTransformer())
    }

}

class MarginItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.right = margin
        outRect.left = margin
    }
}

class ScalePageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val scaleFactor = Math.max(0.85f, 1 - Math.abs(position))
        page.scaleX = scaleFactor
        page.scaleY = scaleFactor
    }
}

class RecommendPagerAdapter(private val recommendImages: List<String>)
    : RecyclerView.Adapter<RecommendPagerAdapter.RecommendViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recommend, parent, false)
        Log.d("RecommendPagerAdapter", "recommendImages: $recommendImages")
        return RecommendViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        val imageUrl = recommendImages[position]
        Glide.with(holder.itemView)
            .load(imageUrl)
            .into(holder.imageView)
    }


    override fun getItemCount(): Int {
        return recommendImages.size
    }

    inner class RecommendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.recommend_imageView)
    }
}
