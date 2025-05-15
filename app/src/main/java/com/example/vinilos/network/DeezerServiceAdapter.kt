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
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DeezerServiceAdapter constructor(context: Context) {
    companion object {
        const val BASE_URL = "https://api.deezer.com/"
        private var instance: DeezerServiceAdapter? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: DeezerServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    suspend fun getMusicians(): List<Musician> = suspendCoroutine { cont ->
        requestQueue.add(getRequest(
            "search/artist?q=Daft%20Punk",
            { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val dataArray = jsonResponse.getJSONArray("data")
                    val list = mutableListOf<Musician>()
                    for (i in 0 until dataArray.length()) {
                        val item = dataArray.getJSONObject(i)
                        list.add(
                            Musician(
                                id = item.getInt("id"),
                                name = item.getString("name"),
                                image = item.getString("picture"),
                                description = item.getString("description"),
                                birthDate = item.getString("birthDate")
                            )
                        )
                    }
                    cont.resume(list)
                } catch (e: Exception) {
                    cont.resumeWithException(e)
                }
            },
            { error ->
                cont.resumeWithException(error)
            }
        ))
    }

    suspend fun getAlbums(): List<Album> = suspendCoroutine { cont ->
        requestQueue.add(getRequest(
            "artist/27/albums",
            { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val dataArray = jsonResponse.getJSONArray("data")
                    val list = mutableListOf<Album>()
                    for (i in 0 until dataArray.length()) {
                        val item = dataArray.getJSONObject(i)
                        list.add(
                            Album(
                                albumId = item.getInt("id"),
                                name = item.getString("title"),
                                cover = item.getString("cover_medium"),
                                recordLabel = item.getString("record_type"),
                                releaseDate = item.getString("release_date"),
                                genre = item.getString("genre_id"),
                                description = item.getString("type")
                            )
                        )
                    }
                    cont.resume(list)
                } catch (e: Exception) {
                    cont.resumeWithException(e)
                }
            },
            { error ->
                cont.resumeWithException(error)
            }
        ))
    }

    private fun getRequest(
        path: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL + path, responseListener, errorListener)
    }
}
