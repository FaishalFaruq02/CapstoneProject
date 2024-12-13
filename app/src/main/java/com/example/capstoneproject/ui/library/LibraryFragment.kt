package com.example.capstoneproject.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.databinding.FragmentLibraryBinding
import com.example.capstoneproject.ui.home.BookAdapter
import com.example.capstoneproject.ui.home.DataItem

class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LibraryViewModel
    private lateinit var adapter: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[LibraryViewModel::class.java]

        setupRecyclerView()

        observeViewModel()

        viewModel.fetchBooks()
    }

    private fun setupRecyclerView() {
        adapter = BookAdapter(emptyList()) { book ->
            addToLibrary(book)
        }
        binding.rvLibrary.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvLibrary.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.books.observe(viewLifecycleOwner) { books ->
            if (books.isNotEmpty()) {
                binding.progressBar.visibility = View.GONE
                adapter.submitList(books.drop(7).take(7))
            } else {
                Toast.makeText(requireContext(), "No books available", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addToLibrary(book: DataItem) {
        val libraryViewModel = ViewModelProvider(requireActivity())[LibraryViewModel::class.java]
        libraryViewModel.addBookToLibrary(book)
        Toast.makeText(requireContext(), "${book.title} added to library!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
