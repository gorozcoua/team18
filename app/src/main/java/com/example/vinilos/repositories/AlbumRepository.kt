package com.example.vinilos.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilos.models.Album
import com.example.vinilos.network.DeezerServiceAdapter

class AlbumRepository (val application: Application){
    fun refreshData(callback:(resp:List<Album>)->Unit, onError: (error: VolleyError)->Unit) {
        DeezerServiceAdapter.getInstance(application).getAlbums({
            callback(it)
        },
            onError
        )
    }
}