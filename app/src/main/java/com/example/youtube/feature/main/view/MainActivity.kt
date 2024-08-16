package com.example.youtube.feature.main.view

import android.graphics.drawable.GradientDrawable.Orientation
import android.icu.lang.UCharacter.VerticalOrientation
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtube.R
import com.example.youtube.databinding.ActivityMainBinding
import com.example.youtube.feature.main.controller.MainController
import com.example.youtube.model.Video

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: MainController
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        controller = MainController(this)
        adapter = MainAdapter(emptyList()){ video ->

        }

        binding.apply {
            mainRvVideo.adapter = adapter
            mainRvVideo.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    fun displayVideoList(videos: List<Video>){
        adapter.items = videos
        adapter.notifyItemChanged(0)
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.onDestroy()
    }

}