package com.example.practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.practice.model.ImageResultData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private val theCatApiService by lazy { retrofit.create(TheCatApiService::class.java) }
    private val imageLoader by lazy { GlideImageLoader(this) }

    private val serverResponseView: TextView by lazy { findViewById(R.id.main_server_response) }
    private val profileImageView: ImageView by lazy { findViewById(R.id.main_profile_image) }
    private val newCatButtonView: Button by lazy { findViewById(R.id.new_cat_button) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newCatButtonView.setOnClickListener { getCatImageResponse() }
    }

    private fun getCatImageResponse() {
        val call = theCatApiService.searchImages(1, "full")
        call.enqueue(object : Callback<List<ImageResultData>> {
            override fun onFailure(call: Call<List<ImageResultData>>, t: Throwable) {
                Log.e(TAG, "Failed to get search results!", t)
            }

            override fun onResponse(
                call: Call<List<ImageResultData>>,
                response: Response<List<ImageResultData>>
            ) {
                if (response.isSuccessful) {
                    val imageResults = response.body()
                    val firstImageUrl = imageResults?.firstOrNull()?.imageUrl ?: ""
                    if (firstImageUrl.isNotEmpty()) {
                        imageLoader.loadImage(firstImageUrl, profileImageView)
                    } else {
                        Log.d(TAG, "Missing image URL")
                    }
                    serverResponseView.text = "IMAGE URL: $firstImageUrl"
                } else {
                    Log.e(
                        TAG,
                        "Failed to get search results\n${response.errorBody()?.string() ?: ""}"
                    )
                }
            }
        })
    }
}

























