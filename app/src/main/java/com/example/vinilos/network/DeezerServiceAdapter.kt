package com.example.vinilos.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinilos.models.Album
import com.example.vinilos.models.Musician
import org.json.JSONArray
import org.json.JSONObject

class DeezerServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= "https://api.deezer.com/"
        var instance: DeezerServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: DeezerServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }
    fun getMusicians(onComplete:(resp:List<Musician>)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("search/artist?q=Daft%20Punk",
            Response.Listener<String> { response ->
                val jsonResponse = JSONObject(response)
                val dataArray = jsonResponse.getJSONArray("data")
                val list = mutableListOf<Musician>()
                for (i in 0 until dataArray.length()) {
                    val item = dataArray.getJSONObject(i)
                    list.add(Musician(
                        id = item.getInt("id"),
                        name = item.getString("name"),
                        image = item.getString("picture")
                    ))
                }
                onComplete(list)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }

    fun getAlbums(onComplete:(resp:List<Album>)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("artist/27/albums",
            Response.Listener<String> { response ->
                val jsonResponse = JSONObject(response)
                val dataArray = jsonResponse.getJSONArray("data")
                val list = mutableListOf<Album>()
                for (i in 0 until dataArray.length()) {
                    val item = dataArray.getJSONObject(i)
                    list.add(Album(
                        albumId = item.getInt("id"),
                        name = item.getString("title"),
                        cover = item.getString("cover_medium"),
                        recordLabel = item.getString("record_type"),
                        releaseDate = item.getString("release_date"),
                        genre = item.getString("genre_id"), // genre_id might be Int
                        description = item.getString("type")
                    ))
                }
                onComplete(list)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }
}