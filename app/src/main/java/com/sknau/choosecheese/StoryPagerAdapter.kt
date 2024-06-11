package com.sknau.choosecheese

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryPagerAdapter(
    private val storyUrls: List<String>,
    private val context: Context,
    private val apiService: QrApiService  // API 서비스 추가
) : RecyclerView.Adapter<StoryPagerAdapter.StoryViewHolder>() {

    inner class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.home_recyclerview_imageview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_viewpager, parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val imageUrl = storyUrls[position]
        Glide.with(holder.imageView.context)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder_image)
            .transform(CircleCrop())
            .into(holder.imageView)

        holder.imageView.setOnClickListener {
            // 이미지 URL을 서버에 전송
            apiService.sendTogetherImages(sendTogetherData(imageUrl)).enqueue(object : Callback<List<String>> {
                override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                    if (response.isSuccessful) {
                        val imageUrls = response.body()?.toTypedArray() ?: arrayOf()
                        val intent = Intent(context, TogetherActivity::class.java).apply {
                            putExtra("imageUrls", imageUrls)
                        }
                        context.startActivity(intent)
                    } else {
                        Log.e("StoryPagerAdapter", "Failed to send image: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    Log.e("StoryPagerAdapter", "Failed to send image click", t)
                }
            })
        }
    }

    override fun getItemCount(): Int = storyUrls.size
}
