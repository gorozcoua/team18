package com.example.vinilos.ui.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.vinilos.R
import com.example.vinilos.models.Musician

class MusicianDetailAdapter(private val view: View) {

    private val musicianImage: ImageView = view.findViewById(R.id.musician_detail_image)
    private val musicianName: TextView = view.findViewById(R.id.musician_detail_name)

    fun bind(musician: Musician) {
        Glide.with(view.context).load(musician.image).into(musicianImage)
        musicianName.text = musician.name
    }
}
