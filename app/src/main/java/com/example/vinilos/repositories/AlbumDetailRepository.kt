package com.example.vinilos.repositories

import android.app.Application
import android.util.Log
import com.example.vinilos.models.Track
import com.example.vinilos.network.NetworkServiceAdapter
import com.example.vinilos.network.CacheManager

class AlbumDetailRepository(val application: Application) {

    suspend fun refreshData(albumId: Int): List<Track> {
        val cache = CacheManager.getInstance(application.applicationContext)
        val potentialResp = cache.getTracks(albumId)

        return if (potentialResp.isEmpty()) {
            try {
                Log.d("Track Cache decision", "get from network")
                Log.d("Track Cache decision", "get from network")
                val tracks = NetworkServiceAdapter.getInstance(application)
                    .getTracksForAlbum(albumId)
                cache.addTracks(albumId, tracks)
                tracks
            } catch (e: Exception) {
                Log.e("NetworkError", "Fallo al obtener datos de red", e)
                emptyList()
            }

        } else {
            Log.d("Track Cache decision", "return ${potentialResp.size} elements from cache")
            potentialResp
        }
    }
}
