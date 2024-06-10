import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sknau.choosecheese.R

class HomeImageAdapter(private val clickListener: (String) -> Unit) : RecyclerView.Adapter<HomeImageAdapter.HomeImageViewHolder>() {

    private val images: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_image, parent, false)
        return HomeImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeImageViewHolder, position: Int) {
        val imageUrl = images[position]
        Glide.with(holder.imageView.context)
            .load(imageUrl)
            .into(holder.imageView)

        holder.imageView.setOnClickListener {
            clickListener(imageUrl)
        }
    }

    override fun getItemCount(): Int = images.size

    fun setImages(newImages: List<String>) {
        images.clear()
        images.addAll(newImages)
        notifyDataSetChanged()
    }

    inner class HomeImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.home_imageView)
    }
}
