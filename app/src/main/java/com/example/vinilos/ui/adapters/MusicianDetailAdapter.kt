package com.example.vinilos.ui.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.vinilos.R
import com.example.vinilos.models.Musician
import java.text.SimpleDateFormat
import java.util.*

class MusicianDetailAdapter(private val view: View) {

    private val musicianImage: ImageView = view.findViewById(R.id.musician_detail_image)
    private val musicianName: TextView = view.findViewById(R.id.musician_detail_name)
    private val musicianDescription: TextView = view.findViewById(R.id.musician_detail_description)
    private val musicianBirthDate: TextView = view.findViewById(R.id.musician_detail_birthdate)

    fun bind(musician: Musician) {
        Glide.with(view.context).load(musician.image).into(musicianImage)
        musicianName.text = musician.name
        musicianDescription.text = musician.description
        musicianBirthDate.text = "Fecha de nacimiento: ${formatBirthDateLegacy(musician.birthDate)}"
    }

    fun formatBirthDateLegacy(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
        val outputFormat = SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale("es", "ES"))
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date ?: Date())
    }
}
