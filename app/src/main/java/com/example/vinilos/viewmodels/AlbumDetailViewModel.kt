package com.example.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.models.Album
import com.example.vinilos.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.lifecycle.viewModelScope
import com.example.vinilos.models.Track
import com.example.vinilos.network.NetworkServiceAdapter
import com.example.vinilos.repositories.AlbumDetailRepository
import kotlinx.coroutines.launch

class AlbumDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _tracks = MutableLiveData<List<Track>>()
    val tracks: LiveData<List<Track>> = _tracks

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    fun loadTracks(albumId: Int) {
        viewModelScope.launch {
            try {
                val repository = AlbumDetailRepository(getApplication())
                val trackList = repository.refreshTracksData(albumId)
                _tracks.postValue(trackList)
                _error.postValue(false)
            } catch (e: Exception) {
                _error.postValue(true)
            }
        }
    }
}
