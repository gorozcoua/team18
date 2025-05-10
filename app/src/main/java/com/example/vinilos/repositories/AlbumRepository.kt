package com.example.vinilos.repositories

import android.app.Application
import android.util.Log
import com.example.vinilos.models.Album
import com.example.vinilos.network.NetworkServiceAdapter
import com.example.vinilos.network.CacheManager

class AlbumRepository (val application: Application){
   suspend fun refreshData(): List<Album> {
        val cache = CacheManager.getInstance(application.applicationContext)
        val potentialResp = cache.getAlbums()

        return if (potentialResp.isEmpty()) {
            Log.d("Track Cache decision", "get from network")
            val albums = NetworkServiceAdapter.getInstance(application)
                .getAlbums() // ← Debes tener un método suspend
            cache.addAlbums(albums)
            albums
        } else {
            Log.d("Track Cache decision", "return ${potentialResp.size} elements from cache")
            potentialResp
        }
    }
}