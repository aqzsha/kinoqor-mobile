package com.example.kinoqor.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinoqor.R
import com.example.kinoqor.data.local.entity.FilmEntity
class FilmAdapter(
    private val onClick: (Long) -> Unit
) : RecyclerView.Adapter<FilmAdapter.VH>() {

    private val items = mutableListOf<FilmEntity>()

    fun setItems(list: List<FilmEntity>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_film, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val film = items[position]
        holder.bind(film)
        holder.itemView.setOnClickListener {
            onClick(film.id)
        }
    }

    override fun getItemCount(): Int = items.size

    class VH(v: View) : RecyclerView.ViewHolder(v) {

        private val poster = v.findViewById<ImageView>(R.id.ivPoster)
        private val name = v.findViewById<TextView>(R.id.tvName)
        private val rate = v.findViewById<TextView>(R.id.tvRate)
        private val age = v.findViewById<TextView>(R.id.tvAge)

        fun bind(film: FilmEntity) {
            name.text = film.name

            rate.visibility = if ((film.rate ?: 0.0) > 0) {
                rate.text = String.format("%.1f", film.rate)
                View.VISIBLE
            } else View.GONE

            age.visibility = if ((film.ageLimit ?: 0) > 0) {
                age.text = "${film.ageLimit}+"
                View.VISIBLE
            } else View.GONE

            Glide.with(itemView)
                .load("http://10.0.2.2:8888${film.posterUrl}")
                .into(poster)
        }
    }
}
