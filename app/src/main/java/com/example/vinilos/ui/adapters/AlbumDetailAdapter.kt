package com.example.vinilos.ui.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.vinilos.R
import com.example.vinilos.models.Album

class AlbumDetailAdapter(private val view: View) {

    private val albumImage: ImageView = view.findViewById(R.id.album_detail_image)
    private val albumName: TextView = view.findViewById(R.id.album_detail_name)
    private val albumGenre: TextView = view.findViewById(R.id.album_detail_genre)
    private val albumLabel: TextView = view.findViewById(R.id.album_detail_label)
    private val albumDescription: TextView = view.findViewById(R.id.album_detail_description)
    private val albumReleaseDate: TextView = view.findViewById(R.id.album_detail_release_date)

    fun bind(album: Album) {
        Glide.with(view.context).load(album.cover).into(albumImage)

        albumName.text = album.name
        albumGenre.text = "Género: ${album.genre}"
        albumLabel.text = "Sello: ${album.recordLabel}"
        albumDescription.text = album.description
        albumReleaseDate.text = "Lanzamiento: ${album.releaseDate.take(10)}"
    }
}
