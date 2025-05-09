package com.example.api_libs.repositories

import android.app.Application
import android.util.Log
import com.example.vinilos.models.Musician
import com.example.vinilos.network.CacheManager
import com.example.vinilos.network.NetworkServiceAdapter

class MusicianRepository (val application: Application){
     suspend fun refreshData(): List<Musician> {
        val cache = CacheManager.getInstance(application.applicationContext)
        val potentialResp = cache.getMusicians()

        return if (potentialResp.isEmpty()) {
            Log.d("Track Cache decision", "get from network")
            val musicians = NetworkServiceAdapter.getInstance(application)
                .getMusicians() // ← Debes tener un método suspend
            cache.addMusicians(musicians)
            musicians
        } else {
            Log.d("Track Cache decision", "return ${potentialResp.size} elements from cache")
            potentialResp
        }
    }

}