package com.sknau.choosecheese

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class CartAdapter(
    private val images: List<PeopleData>
) : RecyclerView.Adapter<CartAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.people_heart_filled_imageView)
        val heartImageView: ImageView = itemView.findViewById(R.id.heart_filled_imageview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart_heart_filled, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageModel = images[position]
        GlideApp.with(holder.imageView.context)
            .load(imageModel.imageUrl)
            .placeholder(R.drawable.placeholder_image)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = images.size
}
