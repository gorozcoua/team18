package com.example.api_libs.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilos.models.Musician
import com.example.vinilos.network.NetworkServiceAdapter

class MusicianRepository (val application: Application){
    suspend fun refreshMusicians(): List<Musician> {
        return NetworkServiceAdapter.getInstance(application).getMusicians()
    }

}