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
import com.example.vinilos.ui.adapters.TrackAdapter
import com.example.vinilos.viewmodels.AlbumDetailViewModel
import com.example.vinilos.models.Album
import com.example.vinilos.ui.adapters.AlbumDetailAdapter


class AlbumDetailFragment : Fragment() {

    private lateinit var albumImage: ImageView
    private lateinit var albumName: TextView
    private lateinit var albumGenre: TextView
    private lateinit var albumLabel: TextView
    private lateinit var albumDescription: TextView
    private lateinit var albumReleaseDate: TextView
    private lateinit var viewModel: AlbumDetailViewModel
    private lateinit var tracksRecyclerView: RecyclerView
    private lateinit var trackAdapter: TrackAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_album_detail, container, false)

        val albumDetailAdapter = AlbumDetailAdapter(view)

        val args = arguments
        if (args != null) {
            val album = Album(
                albumId = args.getInt("albumId", 0),
                name = args.getString("name", ""),
                cover = args.getString("cover", ""),
                releaseDate = args.getString("releaseDate", ""),
                description = args.getString("description", ""),
                genre = args.getString("genre", ""),
                recordLabel = args.getString("recordLabel", ""),
                tracks = emptyList(),
            )
            albumDetailAdapter.bind(album)
        }

        // Setup RecyclerView for tracks
        tracksRecyclerView = view.findViewById(R.id.tracks_recycler_view)
        tracksRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        trackAdapter = TrackAdapter(emptyList())
        tracksRecyclerView.adapter = trackAdapter

        val albumId = arguments?.getInt("albumId") ?: return view

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[AlbumDetailViewModel::class.java]
        viewModel.loadTracks(albumId)
        viewModel.tracks.observe(viewLifecycleOwner) {
            trackAdapter.updateTracks(it)
        }

        return view
    }
}
