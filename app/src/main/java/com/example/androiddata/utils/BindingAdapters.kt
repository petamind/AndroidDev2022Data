package com.example.androiddata.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.NumberFormat

@BindingAdapter("imageURL")
fun loadImage(view: ImageView, imageURL: String) {
    Glide.with(view.context)
        .load(imageURL)
        .into(view)
}


@BindingAdapter("price")
fun itemPrice(view: TextView, price: Double) {
    val formatter = NumberFormat.getCurrencyInstance()
    view.text = "${formatter.format(price)}/each"
}
