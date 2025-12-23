package com.example.kinoqor.ui.cinema

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kinoqor.R
import com.example.kinoqor.data.local.entity.CinemaEntity

class CinemaAdapter(
    private val onClick: (Long) -> Unit
) : RecyclerView.Adapter<CinemaAdapter.VH>() {

    private val items = mutableListOf<CinemaEntity>()

    fun setItems(list: List<CinemaEntity>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cinema, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val cinema = items[position]
        holder.bind(cinema)
        holder.itemView.setOnClickListener { onClick(cinema.id) }
    }

    override fun getItemCount() = items.size

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.tvName)
        private val address = view.findViewById<TextView>(R.id.tvAddress)

        fun bind(c: CinemaEntity) {
            name.text = c.name
            address.text = c.address ?: ""
        }
    }
}
