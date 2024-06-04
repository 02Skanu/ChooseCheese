package com.sknau.choosecheese

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class PeopleAdapter(
    private val images: List<PeopleData>,
    private val onHeartClick: (String) -> Unit
) : RecyclerView.Adapter<PeopleAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.home_imageView)
        val heartImageView: ImageView = itemView.findViewById(R.id.heart_imageview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageModel = images[position]
        GlideApp.with(holder.imageView.context)
            .load(imageModel.imageUrl)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
            .into(holder.imageView)

        holder.heartImageView.setOnClickListener {
            onHeartClick(imageModel.imageUrl)
        }
        holder.heartImageView.setOnClickListener {
            holder.heartImageView.setImageResource(R.drawable.heart_filled) // 하트 이미지를 빨간색으로 변경
        }
    }

    override fun getItemCount(): Int = images.size
}
