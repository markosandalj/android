package com.example.digimonlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Context

class DigimonAdapter(
    private val digimonList: List<Digimon>,
    private val itemClickListener: (Digimon) -> Unit
) : RecyclerView.Adapter<DigimonAdapter.DigimonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DigimonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_digimon, parent, false)
        return DigimonViewHolder(view)
    }

    override fun onBindViewHolder(holder: DigimonViewHolder, position: Int) {
        val digimon = digimonList[position]
        holder.bind(digimon, itemClickListener)
    }

    override fun getItemCount(): Int = digimonList.size

    class DigimonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val digimonNameTextView: TextView = itemView.findViewById(R.id.digimonNameTextView)
        private val digimonTypeTextView: TextView = itemView.findViewById(R.id.digimonTypeTextView)
        private val digimonImageView: ImageView = itemView.findViewById(R.id.digimonImageView)

        fun bind(digimon: Digimon, clickListener: (Digimon) -> Unit) {
            digimonNameTextView.text = digimon.name
            digimonTypeTextView.text = digimon.type
            digimonImageView.setImageResource(getImageResource(digimonImageView.context, digimon.imageResId))
            itemView.setOnClickListener { clickListener(digimon) }
        }

        private fun getImageResource(context: Context, imageName: String): Int {
            return context.resources.getIdentifier(imageName, "drawable", context.packageName)
        }
    }
}
