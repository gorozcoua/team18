package com.example.vinilos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.models.Album
import com.example.vinilos.ui.adapters.AlbumAdapter
import com.example.vinilos.viewmodels.AlbumViewModel

class AlbumFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var errorMessage: TextView
    private lateinit var adapter: AlbumAdapter
    private lateinit var viewModel: AlbumViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_album, container, false)

        // Initialize UI elements
        recyclerView = view.findViewById(R.id.album_list)
        errorMessage = view.findViewById(R.id.error_message)

        // Adapter with click listener
        adapter = AlbumAdapter { selectedAlbum ->
            openAlbumDetailFragment(selectedAlbum)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Safe ViewModel instantiation
        viewModel = ViewModelProvider(
            this,
            AlbumViewModel.Factory(requireActivity().application)
        )[AlbumViewModel::class.java]

        observeViewModel()

        return view
    }

    private fun observeViewModel() {
        viewModel.albums.observe(viewLifecycleOwner) { albums ->
            adapter.setAlbums(albums)
            errorMessage.visibility = View.GONE
        }

        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                errorMessage.visibility = View.VISIBLE
                if (viewModel.isNetworkErrorShown.value == false) {
                    viewModel.onNetworkErrorShown()
                }
            } else {
                errorMessage.visibility = View.GONE
            }
        }
    }

    private fun openAlbumDetailFragment(album: Album) {
        val bundle = Bundle().apply {
            putInt("albumId", album.albumId)
            putString("name", album.name)
            putString("cover", album.cover)
            putString("releaseDate", album.releaseDate)
            putString("description", album.description)
            putString("genre", album.genre)
            putString("recordLabel", album.recordLabel)
        }

        val fragment = AlbumDetailFragment()
        fragment.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}
