package com.example.practice

import android.widget.ImageView

interface ImageLoader {
    fun loadImage(imageUrl:String, imageView: ImageView)
}
