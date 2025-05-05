package com.example.vinilos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vinilos.R
import com.example.vinilos.models.Album
import com.example.vinilos.ui.adapters.AlbumDetailAdapter
import com.example.vinilos.ui.adapters.TrackAdapter
import com.example.vinilos.viewmodels.AlbumDetailViewModel

class AlbumDetailFragment : Fragment() {

    private lateinit var tracksRecyclerView: RecyclerView
    private lateinit var trackAdapter: TrackAdapter
    private lateinit var viewModel: AlbumDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_album_detail, container, false)

        // Obtener los datos del Bundle
        val args = arguments
        val album = Album(
            albumId = args?.getInt("albumId") ?: 0,
            name = args?.getString("name").orEmpty(),
            cover = args?.getString("cover").orEmpty(),
            releaseDate = args?.getString("releaseDate").orEmpty(),
            description = args?.getString("description").orEmpty(),
            genre = args?.getString("genre").orEmpty(),
            recordLabel = args?.getString("recordLabel").orEmpty()
        )

        // Cargar detalles del álbum en vista
        val albumDetailAdapter = AlbumDetailAdapter(view)
        albumDetailAdapter.bind(album)

        // Configurar RecyclerView de tracks
        tracksRecyclerView = view.findViewById(R.id.tracks_recycler_view)
        trackAdapter = TrackAdapter(emptyList())
        tracksRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = trackAdapter
        }

        // Inicializar ViewModel usando Factory personalizada
        viewModel = ViewModelProvider(
            this,
            AlbumDetailViewModel.Factory(requireActivity().application, album.albumId)
        )[AlbumDetailViewModel::class.java]

        viewModel.tracks.observe(viewLifecycleOwner) { tracks ->
            trackAdapter.updateTracks(tracks)
        }

        return view
    }
}
