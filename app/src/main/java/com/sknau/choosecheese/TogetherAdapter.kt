package com.sknau.choosecheese

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TogetherAdapter(private val images: List<String>) : RecyclerView.Adapter<TogetherAdapter.TogetherViewHolder>() {

    inner class TogetherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.home_imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TogetherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_image, parent, false)
        return TogetherViewHolder(view)
    }

    override fun onBindViewHolder(holder: TogetherViewHolder, position: Int) {
        val imageUrl = images[position]
        Glide.with(holder.imageView.context)
            .load(imageUrl)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = images.size
}
