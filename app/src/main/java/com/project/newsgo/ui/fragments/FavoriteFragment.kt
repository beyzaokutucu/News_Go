package com.project.newsgo.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.newsgo.R
import com.project.newsgo.data.entity.Article
import com.project.newsgo.databinding.FragmentFavoriteBinding
import com.project.newsgo.databinding.FragmentHomeBinding
import com.project.newsgo.firebase.FirebaseFirestoreResult
import com.project.newsgo.ui.adapters.FavoriteRecyclerViewAdapter
import com.project.newsgo.ui.adapters.NewsRecyclerViewAdapter
import com.project.newsgo.ui.viewmodels.FavoriteFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoriteFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: FavoriteFragmentViewModel by viewModels()
        viewModel = tempViewModel

    }

    override fun onResume() {
        viewModel.getFavorites()
        super.onResume()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.progressBar2.visibility = View.VISIBLE
        viewModel.news.observe(viewLifecycleOwner) { result ->
            when (result) {
                is FirebaseFirestoreResult.Success<*> -> {
                    if (result.data is List<*>) {
                        binding.favoritesRecyclerView.adapter =
                            FavoriteRecyclerViewAdapter(
                                result.data as List<Article>,
                                requireContext()
                            ) {
                                val direction =
                                    FavoriteFragmentDirections.actionFavoriteFragment3ToDetailFragment(
                                        it
                                    )
                                Navigation.findNavController(view).navigate(direction)
                            }
                        binding.progressBar2.visibility = View.GONE
                    }
                }

                is FirebaseFirestoreResult.Failure -> {
                    Log.e("TAG", result.error)
                }
            }

        }
        return view
    }


}