package com.example.kinoqor.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinoqor.R
import com.example.kinoqor.data.remote.dto.FilmDto
import android.widget.ImageView
import android.widget.Toast
import android.widget.LinearLayout
import android.widget.FrameLayout
import android.widget.Button

class FilmAdapter(private val items: MutableList<FilmDto> = mutableListOf()) :
    RecyclerView.Adapter<FilmAdapter.FilmVH>() {

    var onItemClick: ((FilmDto) -> Unit)? = null

    fun setItems(list: List<FilmDto>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent, false)
        return FilmVH(v)
    }

    override fun onBindViewHolder(holder: FilmVH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class FilmVH(view: View) : RecyclerView.ViewHolder(view) {
        private val ivPoster: ImageView = view.findViewById(R.id.ivPoster)
        private val tvRate: TextView = view.findViewById(R.id.tvRate)
        private val tvAge: TextView = view.findViewById(R.id.tvAge)
        private val tvName: TextView = view.findViewById(R.id.tvName)
        private val tvGenres: TextView = view.findViewById(R.id.tvGenres)

        fun bind(item: FilmDto) {
            tvName.text = item.name
            tvGenres.text = "детектив • боевик"

            val rate = item.details?.rate ?: 0.0
            tvRate.text = if (rate > 0.0) String.format("%.1f ★", rate) else "—"

            val age = item.details?.ageLimit ?: 0
            tvAge.text = if (age > 0) "$age+" else "0+"

            val url = item.posterUrl
            if (!url.isNullOrBlank()) {
                Glide.with(ivPoster.context).load(url).centerCrop().into(ivPoster)
            } else {
                ivPoster.setImageResource(R.drawable.placeholder_poster)
            }

            itemView.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }
}
