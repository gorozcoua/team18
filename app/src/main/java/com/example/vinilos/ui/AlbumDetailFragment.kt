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

        albumImage = view.findViewById(R.id.album_detail_image)
        albumName = view.findViewById(R.id.album_detail_name)
        albumGenre = view.findViewById(R.id.album_detail_genre)
        albumLabel = view.findViewById(R.id.album_detail_label)
        albumDescription = view.findViewById(R.id.album_detail_description)
        albumReleaseDate = view.findViewById(R.id.album_detail_release_date)

        loadAlbumFromArguments()

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

    private fun loadAlbumFromArguments() {
        val args = arguments
        if (args != null) {
            val name = args.getString("name", "")
            val cover = args.getString("cover", "")
            val releaseDate = args.getString("releaseDate", "")
            val description = args.getString("description", "")
            val genre = args.getString("genre", "")
            val recordLabel = args.getString("recordLabel", "")

            Glide.with(this).load(cover).into(albumImage)

            albumName.text = name
            albumGenre.text = "Género: $genre"
            albumLabel.text = "Sello: $recordLabel"
            albumDescription.text = description
            albumReleaseDate.text = "Lanzamiento: ${releaseDate.take(10)}" // solo YYYY-MM-DD
        }
    }
}
