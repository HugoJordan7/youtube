package com.example.youtube.feature.main.view

import android.health.connect.datatypes.units.Percentage
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtube.R
import com.example.youtube.databinding.ActivityMainBinding
import com.example.youtube.databinding.VideoDetailBinding
import com.example.youtube.databinding.VideoDetailContentBinding
import com.example.youtube.di.DependencyInjector
import com.example.youtube.extension.formatTime
import com.example.youtube.feature.main.controller.MainController
import com.example.youtube.model.ListVideo
import com.example.youtube.model.Video
import com.example.youtube.model.videos
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var controller: MainController

    private lateinit var adapter: MainAdapter
    private lateinit var detailAdapter: VideoDetailAdapter

    private lateinit var binding: ActivityMainBinding
    private lateinit var detailBinding: VideoDetailBinding
    private lateinit var detailContentBinding: VideoDetailContentBinding

    private lateinit var youtubePlayer: YoutubePlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.motion_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        detailBinding = VideoDetailBinding.bind(binding.root)
        detailContentBinding = VideoDetailContentBinding.bind(detailBinding.containerScroll)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = ""

        detailBinding.videoLayer.alpha = 0f

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

        preparePlayer()

        detailBinding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) youtubePlayer.seek(progress.toLong())
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

    }

    private fun preparePlayer(){
        youtubePlayer = YoutubePlayer(this)
        youtubePlayer.youtubePlayerListener = object : YoutubePlayer.YoutubePlayerListener{

            override fun onPrepared(duration: Long) {
                detailBinding.durationTime.text = duration.formatTime()
            }

            override fun onTrackTime(currentPosition: Long, percent: Long) {
                detailBinding.seekBar.progress = percent.toInt()
                detailBinding.currentTime.text = currentPosition.formatTime()
            }

        }

        detailBinding.videoSurface.holder.addCallback(youtubePlayer)
    }

    private fun showOverlayView(video: Video){
        binding.motionContainer.setTransitionListener(MotionTransition(detailBinding.videoLayer))

        detailBinding.apply {
            videoLayer.animate().apply {
                duration = 400
                alpha(0.5f)
            }
            youtubePlayer.setUrl(video.videoUrl)
        }

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
        youtubePlayer.release()
    }

    override fun onPause() {
        super.onPause()
        youtubePlayer.pause()
    }

}