package com.sknau.choosecheese

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sknau.choosecheese.R

class HorizontalRecyclerViewAdapter(
    private val items: List<String>  // 이미지 URL 리스트
) : RecyclerView.Adapter<HorizontalRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.home_recyclerview_imageview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_viewpager, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUrl = items[position]
        Glide.with(holder.imageView.context)
            .load(imageUrl)
            .centerCrop()
            .into(holder.imageView)

    }

    override fun getItemCount(): Int = items.size
}
