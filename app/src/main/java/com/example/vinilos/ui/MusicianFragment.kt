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
import com.example.vinilos.ui.adapters.MusicianAdapter
import com.example.vinilos.viewmodels.MusicianViewModel

class MusicianFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var errorMessage: TextView
    private lateinit var adapter: MusicianAdapter
    private lateinit var viewModel: MusicianViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_musician, container, false)

        // Initialize UI elements
        recyclerView = view.findViewById(R.id.musician_list)
        errorMessage = view.findViewById(R.id.error_message)
        adapter = MusicianAdapter()

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Safe ViewModel instantiation
        viewModel = ViewModelProvider(
            this,
            MusicianViewModel.Factory(requireActivity().application)
        )[MusicianViewModel::class.java]

        observeViewModel()

        return view
    }

    private fun observeViewModel() {
        viewModel.musicians.observe(viewLifecycleOwner) { musicians ->
            adapter.setMusicians(musicians)
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
}