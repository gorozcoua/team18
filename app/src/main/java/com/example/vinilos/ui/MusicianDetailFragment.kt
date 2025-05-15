package com.example.vinilos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.models.Musician
import com.example.vinilos.ui.adapters.MusicianDetailAdapter
import com.example.vinilos.ui.adapters.MusicianAlbumAdapter
import com.example.vinilos.viewmodels.MusicianDetailViewModel

class MusicianDetailFragment : Fragment() {

    private lateinit var albumsRecyclerView: RecyclerView
    private lateinit var albumAdapter: MusicianAlbumAdapter
    private lateinit var viewModel: MusicianDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_musician_detail, container, false)

        val args = arguments
        val musician = Musician(
            id = args?.getInt("musicianId") ?: 0,
            name = args?.getString("name").orEmpty(),
            image = args?.getString("image").orEmpty(),
            description = args?.getString("description").orEmpty(),
            birthDate = args?.getString("birthDate").orEmpty(),
        )

        // Bind basic musician info to the view
        val detailAdapter = MusicianDetailAdapter(view)
        detailAdapter.bind(musician)

        // Set up albums (or related info) list
        albumsRecyclerView = view.findViewById(R.id.albums_recycler_view)
        albumAdapter = MusicianAlbumAdapter (emptyList())
        albumsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = albumAdapter
        }

        // ViewModel for this musician
        viewModel = ViewModelProvider(
            this,
            MusicianDetailViewModel.Factory(requireActivity().application, musician.id)
        )[MusicianDetailViewModel::class.java]

        viewModel.albums.observe(viewLifecycleOwner) { albums ->
            albumAdapter.setAlbums(albums)
        }

        return view
    }
}
