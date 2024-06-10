package com.sknau.choosecheese

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RankAdapter(private var ranks: List<RankItem>) : RecyclerView.Adapter<RankAdapter.RankViewHolder>() {

    fun updateRanks(newRanks: List<RankItem>) {
        ranks = newRanks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rank, parent, false)
        return RankViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        val rank = ranks[position]
        holder.bind(rank)
    }

    override fun getItemCount(): Int {
        return ranks.size
    }

    class RankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val rankTextView: TextView = itemView.findViewById(R.id.item_rank)
        private val personImageView: ImageView = itemView.findViewById(R.id.item_rank_person)
        private val nameTextView: TextView = itemView.findViewById(R.id.item_rank_info_name)
        private val scoreTextView: TextView = itemView.findViewById(R.id.item_rank_info_money)
        private val misoTextView: TextView = itemView.findViewById(R.id.item_rank_info_miso)

        fun bind(rank: RankItem) {
            rankTextView.text = rank.rank.toString()
            nameTextView.text = rank.username
            scoreTextView.text = "Score: ${rank.total_miso}"
            misoTextView.text = "미소"

            Glide.with(itemView.context)
                .load(rank.emoji)
                .into(personImageView)
        }
    }
}
