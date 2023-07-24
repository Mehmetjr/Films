package com.example.films.ui.detail


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.example.films.R
import com.example.films.databinding.FragmentDetailBinding
import com.example.films.models.FilmDetail
import com.example.films.ui.baseclass.BaseFragment
import com.example.films.util.Constants
import com.example.films.util.PreferenceHelper
import com.example.films.util.loadImage
import com.example.films.util.viewBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val viewModel by viewModel<DetailViewModel>()

    private val sharedPref: PreferenceHelper by inject<PreferenceHelper>()

    val favoriteList by lazy {
        sharedPref.getData<MutableList<Double>>("Fav") ?: mutableListOf<Double>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //   viewModel.getFilmDetail(filmId = args.filmid)
        observeEvents()
        listeners()


    }

   private fun listeners() {
        binding.apply {
            imgFav.setOnClickListener {


                currentItem?.isFav = currentItem?.isFav?.not()

                updateFavoriteButton(currentItem?.isFav == true)
                if (currentItem?.isFav == true) {
                    currentItem?.id?.let {
                        favoriteList?.add(it.toDouble())
                        sharedPref.saveOrDelete("Fav", favoriteList)
                    }
                } else {
                    currentItem?.id?.let {
                        favoriteList?.remove(it.toDouble())
                        sharedPref.saveOrDelete("Fav", favoriteList)
                    }

                }
                val input = currentItem?.isFav
                val bundle = Bundle()
                currentItem?.id?.let {
                    bundle.putInt(Constants.ITEM_KEY, it)
                    if (input != null) {
                        bundle.putBoolean(Constants.BUNDLE_KEY, input)
                    }
                }
                parentFragmentManager.setFragmentResult(Constants.REQUEST_KEY, bundle)

            }
        }
    }


    private fun updateFavoriteButton(isFavorite: Boolean) {
        if (isFavorite) {
            binding.imgFav.setImageResource(R.drawable.favheart)

        } else {
            binding.imgFav.setImageResource(R.drawable.emptyheart)
        }
    }

    var currentItem: FilmDetail? = null
    private fun observeEvents() {

        viewModel.getFilmDetailState().observe(viewLifecycleOwner) {

            if (it.isStatusSuccess) {
                currentItem = it.data
                if (favoriteList?.contains(it.data?.id?.toDouble()) == true) {
                    updateFavoriteButton(true)
                    currentItem?.isFav = true
                } else {
                    updateFavoriteButton(false)
                    currentItem?.isFav = false
                }


                binding.imgDetailPoster.loadImage(it.data?.posterPath)
                binding.txtFilmDetail.text = it.data?.overview
                binding.txtFilmName.text = it.data?.title
                binding.txtReleaseDate.text = it.data?.releaseDate
                binding.movieDetailGroup.isVisible = true
                binding.progressBarDetail.isVisible = false

            } else binding.progressBarDetail.isVisible = it.isStatusLoading
        }


    }


}