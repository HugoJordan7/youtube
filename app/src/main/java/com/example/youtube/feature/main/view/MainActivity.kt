package com.example.youtube.feature.main.view

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtube.R
import com.example.youtube.databinding.ActivityMainBinding
import com.example.youtube.databinding.VideoDetailBinding
import com.example.youtube.databinding.VideoDetailContentBinding
import com.example.youtube.di.DependencyInjector
import com.example.youtube.feature.main.controller.MainController
import com.example.youtube.model.ListVideo
import com.example.youtube.model.Video
import com.example.youtube.model.videos
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: MainController
    private lateinit var adapter: MainAdapter
    private lateinit var detailAdapter: VideoDetailAdapter
    private lateinit var videoBinding: VideoDetailBinding
    private lateinit var detailContentBinding: VideoDetailContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        videoBinding = VideoDetailBinding.bind(binding.root)
        detailContentBinding = VideoDetailContentBinding.bind(videoBinding.containerScroll)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = ""

        videoBinding.videoLayer.alpha = 0f

        adapter = MainAdapter(emptyList()){ video ->
            showOverlayView(video)
        }
        binding.apply {
            mainRvVideo.adapter = adapter
            mainRvVideo.layoutManager = LinearLayoutManager(this@MainActivity)
        }

        detailAdapter = VideoDetailAdapter(videos)
        detailContentBinding.apply {
            videoDetailRv.adapter = detailAdapter
            videoDetailRv.layoutManager = LinearLayoutManager(this@MainActivity)
        }

        val repository = DependencyInjector.provideMainRepository()
        controller = MainController(this, repository)
        controller.findVideoList()

    }

    private fun showOverlayView(video: Video){
        videoBinding.videoLayer.animate().apply {
            duration = 400
            alpha(0.5f)
        }
        binding.motionContainer.setTransitionListener(MotionTransition(videoBinding.videoLayer))
        detailContentBinding.apply {
            videoDetailTitle.text = video.title
            channelName.text = video.publisher.name
            Picasso.get().load(video.publisher.pictureProfileUrl).into(channelLogo)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.onDestroy()
    }

}