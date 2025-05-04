package com.example.vinilos.network

import android.content.Context
import com.example.vinilos.models.Track

class CacheManager (context: Context){
    companion object {
        private var instance: CacheManager? = null

        fun getInstance(context: Context): CacheManager =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }

    private var tracks: HashMap<Int, List<Track>> = hashMapOf()

    fun addTracks(albumId: Int, trackList: List<Track>) {
        if (!tracks.containsKey(albumId)) {
            tracks[albumId] = trackList
        }
    }

    fun getTracks(albumId: Int): List<Track> {
        return tracks[albumId] ?: emptyList()
    }

    fun contains(albumId: Int): Boolean {
        return tracks.containsKey(albumId)
    }
}