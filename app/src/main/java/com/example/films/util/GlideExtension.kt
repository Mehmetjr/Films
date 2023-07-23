package com.example.films.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.centerCropTransform
import com.example.films.R

val options = RequestOptions()
    .fitCenter()
    .override(600, 400)

fun ImageView.loadImage(path: String?) {
    Glide.with(this.context).load(Constants.IMAGE_BASE_URL + path)
        .apply(options.error(R.drawable.ic_error)).into(this)
}