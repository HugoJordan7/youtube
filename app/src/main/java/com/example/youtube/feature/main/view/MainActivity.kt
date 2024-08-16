package com.example.youtube.feature.main.view

import android.graphics.drawable.GradientDrawable.Orientation
import android.icu.lang.UCharacter.VerticalOrientation
import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtube.R
import com.example.youtube.base.RequestCallback
import com.example.youtube.databinding.ActivityMainBinding
import com.example.youtube.feature.main.controller.MainController
import com.example.youtube.feature.main.data.MainDataSource
import com.example.youtube.feature.main.data.MainRepository
import com.example.youtube.model.ListVideo
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

        //TODO: after create repository || controller = MainController(this)

        adapter = MainAdapter(emptyList()){ video ->

        }

        binding.apply {
            mainRvVideo.adapter = adapter
            mainRvVideo.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    fun showProgress(enabled: Boolean){
        binding.mainProgress.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    fun displayVideoList(videos: ListVideo){
        adapter.items = videos.data
        adapter.notifyItemChanged(0)
    }

    fun displayFailure(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.onDestroy()
    }

}