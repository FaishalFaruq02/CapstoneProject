package com.example.capstoneproject.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.databinding.FragmentHomeBinding
import com.example.capstoneproject.ui.library.LibraryViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter1: BookAdapter
    private lateinit var adapter2: BookAdapter
    private lateinit var adapter3: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        setupRecyclerView()

        observeViewModel()

        viewModel.fetchBooks()

        val navigateTo = activity?.intent?.getStringExtra("navigate_to")
        if (navigateTo == "home") {
            Toast.makeText(requireContext(), "Navigated to Home after upload!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        adapter1 = BookAdapter(emptyList()) { book ->
            addToLibrary(book)
        }
        binding.rvBooksHorizontal1.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvBooksHorizontal1.adapter = adapter1

        adapter2 = BookAdapter(emptyList()) { book ->
            addToLibrary(book)
        }
        binding.rvBooksHorizontal2.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvBooksHorizontal2.adapter = adapter2

        adapter3 = BookAdapter(emptyList()) { book ->
            addToLibrary(book)
        }
        binding.rvBooksHorizontal3.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvBooksHorizontal3.adapter = adapter3
    }

    private fun observeViewModel() {
        viewModel.books.observe(viewLifecycleOwner) { books ->
            if (books.isNotEmpty()) {
                binding.pbSection1.visibility = View.GONE
                binding.pbSection2.visibility = View.GONE
                binding.pbSection3.visibility = View.GONE

                adapter1.submitList(books.drop(1).take(8))
                adapter2.submitList(books.drop(9).take(5))
                adapter3.submitList(books.takeLast(8))
            } else {
                Toast.makeText(requireContext(), "No books available", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.pbSection1.visibility = View.VISIBLE
                binding.pbSection2.visibility = View.VISIBLE
                binding.pbSection3.visibility = View.VISIBLE
            } else {
                binding.pbSection1.visibility = View.GONE
                binding.pbSection2.visibility = View.GONE
                binding.pbSection3.visibility = View.GONE
            }
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

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.setDecorFitsSystemWindows(false)
            requireActivity().window.insetsController?.let { controller ->
                controller.hide(WindowInsetsController.BEHAVIOR_SHOW_BARS_BY_SWIPE)
            }
        } else {
            @Suppress("DEPRECATION")
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}