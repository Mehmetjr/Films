package com.example.films.ui.home


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import com.example.films.adapter.FilmAdapter
import com.example.films.databinding.FragmentHomeBinding
import com.example.films.models.Film
import com.example.films.models.FilmItem
import com.example.films.network.Resource
import com.example.films.ui.baseclass.BaseFragment
import com.example.films.util.Constants
import com.example.films.util.PreferenceHelper
import com.example.films.util.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment(R.layout.fragment_home) {


    private val binding by viewBinding(FragmentHomeBinding::bind)
    private var checker: Boolean = true
    private val adapter by lazy {
        FilmAdapter({

            it?.let {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                findNavController().navigate(action)
            }

        },
            {
                if (it.isFav == true) {
                    it.id?.let { id ->
                        favoriteList.add(id.toDouble())
                        sharedPref.saveOrDelete("Fav", favoriteList)
                    }
                } else {
                    it.id?.let { id ->
                        favoriteList.remove(id.toDouble())
                        sharedPref.saveOrDelete("Fav", favoriteList)
                    }

                }
            })
    }
    private val sharedPref: PreferenceHelper by inject<PreferenceHelper>()

    private val favoriteList by lazy {
        sharedPref.getData<MutableList<Double>>("Fav") ?: mutableListOf<Double>()
    }


    private val viewModel by viewModel<HomeViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEvents()

        parentFragmentManager.setFragmentResultListener(Constants.REQUEST_KEY, this,
            FragmentResultListener { requestKey, result ->
                val listener: Boolean = result.getBoolean(Constants.BUNDLE_KEY)
                val itemId: Int = result.getInt(Constants.ITEM_KEY)
                var value = adapter.currentList
                val item = value.find {
                    it.id == itemId
                }
                item?.isFav = listener
                if (item?.isFav == true) {
                    favoriteList.add(itemId.toDouble())
                } else {
                    favoriteList.remove(itemId.toDouble())
                }



            })


        lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, " ", Toast.LENGTH_LONG).show()
            }

        }

        binding.recyclerView.adapter = adapter
        binding.floatingActionButton.setOnClickListener {
            checker = !checker

            if (checker) {
                binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
            } else {
                binding.recyclerView.layoutManager = LinearLayoutManager(context)

            }
        }

    }


    private fun observeEvents() {

        viewModel.getFilmListState().observe(viewLifecycleOwner) {

            if (it.isStatusSuccess) {
                val data = it.data?.filmItems
                data?.forEachIndexed { i, item ->
                    if (favoriteList.contains(item.id?.toDouble())) {
                        item.isFav = true
                        data[i] = item

                    } else {
                        item.isFav = false
                    }
                }
                adapter.submitList(data)

            }


        }
    }


}
