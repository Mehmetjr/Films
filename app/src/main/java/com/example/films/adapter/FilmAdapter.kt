package com.example.films.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.films.R
import com.example.films.databinding.ListItemBinding
import com.example.films.models.FilmItem
import com.example.films.util.Constants
import com.example.films.util.loadImage


class FilmAdapter(
    private val movieClickListener: (item: Int?) -> Unit,
    private val favClickListener: (item: FilmItem) -> Unit,

    ) : ListAdapter<FilmItem, FilmAdapter.ViewHolder>(differCallback) {


    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<FilmItem>() {
            override fun areItemsTheSame(oldItem: FilmItem, newItem: FilmItem): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: FilmItem, newItem: FilmItem): Boolean {
                return oldItem.id == newItem.id
            }

        }

    }

    inner class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilmItem) {
            binding.txtMainName.text = item.title
            binding.imageView.setOnClickListener {
                currentList[bindingAdapterPosition].isFav= item.isFav?.not()
                notifyItemChanged(bindingAdapterPosition)
                favClickListener.invoke(item)
            }
            setFavImage(item)
            binding.imageView
            binding.imgMain.loadImage(item.posterPath)


        }

        private fun setFavImage(item: FilmItem) {
            if (item.isFav == true) {
                binding.imageView.setImageResource(R.drawable.favheart)

            } else {
                binding.imageView.setImageResource(R.drawable.emptyheart)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener {
                movieClickListener.invoke(getItem(bindingAdapterPosition).id)
            }
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))


    }


}


