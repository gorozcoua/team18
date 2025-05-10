package com.example.vinilos.network

import android.content.Context
import com.example.vinilos.models.Album
import com.example.vinilos.models.Musician
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
    private var albums: HashMap<String, List<Album>> = hashMapOf()
    private var musicians: HashMap<String, List<Musician>> = hashMapOf()

    fun addTracks(albumId: Int, trackList: List<Track>) {
        if (!tracks.containsKey(albumId)) {
            tracks[albumId] = trackList
        }
    }

    fun getTracks(albumId: Int): List<Track> {
        return tracks[albumId] ?: emptyList()
    }

    fun addAlbums(albumList: List<Album>) {
        if (!albums.containsKey("albums")) {
            albums["albums"] = albumList
        }
    }

    fun getAlbums(): List<Album> {
        return albums["albums"] ?: emptyList()
    }

    fun addMusicians(musicianList: List<Musician>) {
        if (!musicians.containsKey("musicians")) {
            musicians["musicians"] = musicianList
        }
    }

    fun getMusicians(): List<Musician> {
        return musicians["musicians"] ?: emptyList()
    }
}