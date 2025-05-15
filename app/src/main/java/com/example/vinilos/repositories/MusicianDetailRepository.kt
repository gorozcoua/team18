package com.example.vinilos.repositories

import android.app.Application
import android.util.Log
import com.example.vinilos.models.Album
import com.example.vinilos.network.NetworkServiceAdapter
import com.example.vinilos.network.CacheManager

class MusicianDetailRepository(val application: Application) {

    suspend fun refreshData(musicianId: Int): List<Album> {
        val cache = CacheManager.getInstance(application.applicationContext)
        val potentialResp = cache.getAlbumsForMusician(musicianId)

        return if (potentialResp.isEmpty()) {
            try {
                Log.d("MusicianAlbum Cache", "Fetching from network")
                val albums = NetworkServiceAdapter.getInstance(application)
                    .getAlbumsForMusician(musicianId)
                cache.addAlbumsForMusician(musicianId, albums)
                albums
            } catch (e: Exception) {
                Log.e("NetworkError", "Error fetching musician's albums", e)
                emptyList()
            }
        } else {
            Log.d("MusicianAlbum Cache", "Returning ${potentialResp.size} albums from cache")
            potentialResp
        }
    }
}
