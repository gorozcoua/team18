package com.example.vinilos.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.models.Album

class MusicianAlbumAdapter(private var albums: List<Album>) :
    RecyclerView.Adapter<MusicianAlbumAdapter.AlbumViewHolder>() {

    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val albumName: TextView = view.findViewById(R.id.musician_album_name)
        val recordLabel: TextView = view.findViewById(R.id.musician_album_recordLabel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_musician_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]
        holder.albumName.text = album.name
        holder.recordLabel.text = album.recordLabel
    }

    override fun getItemCount(): Int = albums.size

    fun setAlbums(newAlbums: List<Album>) {
        albums = newAlbums
        notifyDataSetChanged()
    }
}
