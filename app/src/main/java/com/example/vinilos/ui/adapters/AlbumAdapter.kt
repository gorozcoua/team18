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
import com.example.vinilos.models.Album

class AlbumAdapter(private val onAlbumClick: (Album) -> Unit) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    private var albums: List<Album> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setAlbums(albumList: List<Album>) {
        albums = albumList
        notifyDataSetChanged()
    }

    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val albumImage: ImageView = view.findViewById(R.id.album_image)
        val albumButton: Button = view.findViewById(R.id.album_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]
        holder.albumButton.text = album.name

        // Cargar imagen desde URL con Glide con estrategia de cache en disco
        Glide.with(holder.itemView.context)
            .load(album.cover)
            .apply(RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(holder.albumImage)

        // Acción al hacer clic en el botón
        holder.albumButton.setOnClickListener {
            // Aquí puedes hacer algo, como navegar o mostrar detalles
            onAlbumClick(album)
        }
    }

    override fun getItemCount(): Int = albums.size
}