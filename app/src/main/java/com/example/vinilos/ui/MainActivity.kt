package com.example.vinilos.ui

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vinilos.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MusicianFragment())
            .commit()

        val btnMusicians = findViewById<Button>(R.id.btn_musicians)
        val btnAlbums = findViewById<Button>(R.id.btn_albums)

        btnMusicians.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MusicianFragment())
                .commit()
        }

        btnAlbums.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AlbumFragment())
                .commit()
        }
    }
}