package com.aldev.adaberita.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("image")
fun loadNewsImage(view: ImageView, url: String) {
    Glide.with(view)
        .load(url)
        .into(view)
}