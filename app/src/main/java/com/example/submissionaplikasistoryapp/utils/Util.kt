package com.example.submissionaplikasistoryapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submissionaplikasistoryapp.R
import java.text.SimpleDateFormat

//load image in image view
fun getCircularDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 48f
        setTint(context.resources.getColor(R.color.navy_200))
        start()
    }
}

fun ImageView.loadImage(url: String, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_launcher_background)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, url: String?) {
    if (url != null) {
        imageView.loadImage(url, getCircularDrawable(imageView.context))
    }
}

@BindingAdapter("formatDate")
@SuppressLint("SimpleDateFormat")
fun formatDate(textView: TextView, createdAt: String?) {
    if (createdAt != null) {
        val originalFormatDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val targetFormatDate = SimpleDateFormat("dd MMMM yyyy")
        val date = originalFormatDate.parse(createdAt)
        textView.text = targetFormatDate.format(date!!)
    }
}
