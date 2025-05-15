package com.example.vinilos.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinilos.models.Album
import com.example.vinilos.models.Musician
import com.example.vinilos.models.Track
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.suspendCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= "https://apimusica-cucyf3bvgfcdaeeq.canadacentral-01.azurewebsites.net/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }
    suspend fun getMusicians(): List<Musician> = suspendCoroutine { cont ->
        requestQueue.add(getRequest("musicians",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Musician>()
                var item: JSONObject? = null
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    Log.d("Response", item.toString())
                    list.add(Musician(
                        id = item.getInt("id"),
                        name = item.getString("name"),
                        image = item.getString("image"),
                        description = item.getString("image"),
                        birthDate = item.getString("image")
                    ))
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }
        ))
    }


    suspend fun getAlbums() = suspendCoroutine<List<Album>>{ cont->
        requestQueue.add(getRequest("albums",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                var item: JSONObject? = null
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    Log.d("Response", item.toString())
                    list.add(i, Album(
                        albumId = item.getInt("id"),
                        name = item.getString("name"),
                        cover = item.getString("cover"),
                        recordLabel = item.getString("recordLabel"),
                        releaseDate = item.getString("releaseDate"),
                        genre = item.getString("genre"),
                        description = item.getString("description")))
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getTracksForAlbum(albumId: Int): List<Track> = suspendCoroutine { cont ->
        requestQueue.add(getRequest("albums/$albumId/tracks",
            { response ->
                val jsonArray = JSONArray(response)
                val tracks = mutableListOf<Track>()
                var item: JSONObject? = null
                for (i in 0 until jsonArray.length()) {
                    item = jsonArray.getJSONObject(i)
                    Log.d("Response", item.toString())
                    tracks.add(Track(
                        id = item.getInt("id"),
                        name = item.getString("name"),
                        duration = item.getString("duration")))
                }
                cont.resume(tracks)
            },
            {
                cont.resumeWithException(it)
            }
        ))
    }

    suspend fun getAlbumsForMusician(musicianId: Int): List<Album> = suspendCoroutine { cont ->
        requestQueue.add(getRequest("musicians/$musicianId/albums",
            { response ->
                val jsonArray = JSONArray(response)
                val albums = mutableListOf<Album>()
                var item: JSONObject?
                for (i in 0 until jsonArray.length()) {
                    item = jsonArray.getJSONObject(i)
                    Log.d("Response", item.toString())
                    albums.add(Album(albumId = item.getInt("id"), name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description")))
                }
                cont.resume(albums)
            },
            {
                cont.resumeWithException(it)
            }
        ))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }
}