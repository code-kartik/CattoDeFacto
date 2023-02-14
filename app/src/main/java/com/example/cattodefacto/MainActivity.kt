package com.example.cattodefacto

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFact()
    }

    private fun loadFact(){
        val loadingImage:ProgressBar = findViewById(R.id.progressBar)
        loadingImage.visibility = View.VISIBLE

        val loadingFact:ProgressBar = findViewById(R.id.progressBar2)
        loadingFact.visibility = View.VISIBLE

        val gif:ImageView = findViewById(R.id.gif)
        Glide.with(this).load("https://media.tenor.com/ZZu2QC-efdUAAAAi/cute-cat-white.gif").into(gif)

        val fact:TextView = findViewById(R.id.fact)
        val queue = Volley.newRequestQueue(this)
        val factsURL = "https://catfact.ninja/fact"
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, factsURL, null,
            { response ->
                val insertFact = response.getString("fact")
                fact.text = insertFact
                loadingFact.visibility = View.GONE
            },
            { fact.text = "That didn't work!" })

        queue.add(jsonRequest)


        val cat:ImageView = findViewById(R.id.catImage)
        val imageURL = "https://cataas.com/cat?json=true"
        val queueImage = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, imageURL, null,
            { response ->
                val url = response.getString("url")
                Glide.with(this).load("https://cataas.com/$url").listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        loadingImage.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        loadingImage.visibility = View.GONE
                        return false
                    }
                })
                    .into(cat)

            },
            { Glide.with(this).load("https://img.freepik.com/free-vector/oops-404-error-with-broken-robot-concept-illustration_114360-5529.jpg?w=2000").into(cat) })

        queueImage.add(jsonObjectRequest)
    }


    fun nextFact(view: View) {
        loadFact()
    }
}