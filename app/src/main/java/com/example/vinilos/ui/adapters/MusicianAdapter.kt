package com.example.vinilos.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.vinilos.R
import com.example.vinilos.models.Musician

class MusicianAdapter : RecyclerView.Adapter<MusicianAdapter.MusicianViewHolder>() {

    private var musicians: List<Musician> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setMusicians(musicianList: List<Musician>) {
        musicians = musicianList
        notifyDataSetChanged()
    }

    class MusicianViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val musicianImage: ImageView = view.findViewById(R.id.musician_image)
        val musicianButton: Button = view.findViewById(R.id.musician_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicianViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_musician, parent, false)
        return MusicianViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicianViewHolder, position: Int) {
        val musician = musicians[position]
        holder.musicianButton.text = musician.name

        // Cargar imagen desde URL con Glide
        Glide.with(holder.itemView.context)
            .load(musician.image)
            .apply(RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(holder.musicianImage)

        // Acción al hacer clic en el botón (opcional)
        holder.musicianButton.setOnClickListener {
            // Aquí puedes mostrar detalles, navegar o lanzar un Toast
        }
    }

    override fun getItemCount(): Int = musicians.size
}