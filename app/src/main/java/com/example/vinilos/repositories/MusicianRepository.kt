package com.example.api_libs.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilos.models.Musician
import com.example.vinilos.network.DeezerServiceAdapter

class MusicianRepository (val application: Application){
    fun refreshData(callback:(resp:List<Musician>)->Unit, onError: (error: VolleyError)->Unit) {
        DeezerServiceAdapter.getInstance(application).getMusicians({
            callback(it)
        },
            onError
        )
    }
}