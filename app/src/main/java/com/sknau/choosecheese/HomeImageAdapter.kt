import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sknau.choosecheese.R

class HomeImageAdapter : RecyclerView.Adapter<HomeImageAdapter.ImageViewHolder>() {

    private val images = mutableListOf<String>()

    // 어댑터를 통해 이미지 데이터 설정
    fun addImages(newImages: List<String>?) {
        newImages?.let {
            val startPosition = images.size
            images.addAll(it)
            notifyItemRangeInserted(startPosition, it.size)
        }
    }

    // 뷰홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_image, parent, false)
        return ImageViewHolder(view)
    }

    // 뷰홀더와 데이터 바인딩
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }

    // 이미지 뷰홀더 정의
    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(imageUrl: String) {
            Glide.with(itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .into(itemView.findViewById(R.id.home_imageView))
        }
    }
}
