package com.project.newsgo.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.project.newsgo.R
import com.project.newsgo.databinding.FragmentDetailBinding
import com.project.newsgo.databinding.FragmentFavoriteBinding
import com.project.newsgo.databinding.FragmentHomeBinding
import com.project.newsgo.firebase.FirebaseFirestoreResult
import com.project.newsgo.ui.viewmodels.DetailFragmentViewModel
import com.project.newsgo.ui.viewmodels.FavoriteFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var isFavorite = false

    private lateinit var viewModel: DetailFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: DetailFragmentViewModel by viewModels()
        viewModel = tempViewModel

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val bundle: DetailFragmentArgs by navArgs()
        val news = bundle.newsArg
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view=binding.root
        Glide.with(requireContext()).load(news.urlToImage).error(R.drawable.newsplaceholder_ic).into(binding.newsImageView);
        binding.newsTitleTextView.text=news.title
        binding.newsContentTextView.text=news.description
        binding.authorNameTextView.text=truncateText(news.author)
        binding.dateTextView.text=convertDateTime(news.publishedAt)
        binding.backButton.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
        binding.favoriteButton.setOnClickListener {
            if (!isFavorite) {
                viewModel.insertFavorite(news)
            } else {
                viewModel.deleteFavorite(news)
            }
        }
        binding.goSourceButton.setOnClickListener {
            if (!news.url.isNullOrBlank()){
                val direction =
                    DetailFragmentDirections.actionDetailFragmentToSourceFragment(news.url)
                Navigation.findNavController(view).navigate(direction)
            }

        }
        viewModel.checkFavorited(news)
        viewModel.isFavorite.observe(viewLifecycleOwner) { result ->
            when (result) {
                is FirebaseFirestoreResult.Success<*> -> {
                    if (result.data is Boolean) {
                        isFavorite = result.data
                        if (isFavorite) {
                            binding.favoriteButton.setImageResource(R.drawable.filled_ic)
                        } else {
                            binding.favoriteButton.setImageResource(R.drawable.empty_ic)
                        }
                    }
                }

                is FirebaseFirestoreResult.Failure -> {
                    Log.e("TAG", "Hata meydana geldi")
                }
            }

        }
        return view

    }

    private fun convertDateTime(input: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val outputFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm")

        val dateTime = LocalDateTime.parse(input, inputFormatter)
        return dateTime.format(outputFormatter)
    }

    fun truncateText(text: String, maxLength: Int = 15): String {
        return if (text.length > maxLength) {
            text.substring(0, maxLength) + "..."
        } else {
            text
        }
    }


}