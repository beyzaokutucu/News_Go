package com.project.newsgo.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.newsgo.data.entity.Article
import com.project.newsgo.databinding.FragmentHomeBinding
import com.project.newsgo.ui.adapters.NewsRecyclerViewAdapter
import com.project.newsgo.ui.viewmodels.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeFragmentViewModel by viewModels()
    private val handler = Handler()

    val visibleThreshold = 3
    private var searchRunnable: Runnable? = null

    override fun onResume() {
        super.onResume()
        viewModel.getNews()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        val layoutManager = LinearLayoutManager(requireContext())
        val newsRecyclerView = binding.newsRecyclerView
        newsRecyclerView.layoutManager = layoutManager
        Log.e("TAG", "onCreate")
        viewModel.news.observe(viewLifecycleOwner) { listArticle ->

            if (viewModel.currentPage == 1) {
                Log.e("TAG", "if ")
                val adapter = NewsRecyclerViewAdapter(listArticle, requireContext()) {
                    val direction = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                    Navigation.findNavController(view).navigate(direction)
                }
                newsRecyclerView.adapter = adapter
            } else {
                Log.e("TAG", "else ")
                Log.e("TAG", "${listArticle.size}")
                (newsRecyclerView.adapter as? NewsRecyclerViewAdapter)?.updateData(listArticle)
            }
            binding.progressBar2.visibility = View.GONE

        }


        newsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!viewModel.isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    viewModel.currentPage++
                    viewModel.getNews()
                }
            }
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchRunnable?.let { handler.removeCallbacks(it) }
                searchRunnable = Runnable {
                    if (!newText.isNullOrBlank()) {
                        viewModel.query = newText
                        viewModel.currentPage = 1
                        viewModel.getNews()
                    }
                }.also { handler.postDelayed(it, 1000) }
                return true
            }
        })
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        resetPage()
        _binding = null
    }

    private fun resetPage() {
        viewModel.currentPage = 1
        viewModel.news = MutableLiveData<List<Article>>()
    }
}
