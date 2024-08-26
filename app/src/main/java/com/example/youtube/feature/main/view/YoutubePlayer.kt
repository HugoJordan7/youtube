package com.example.youtube.feature.main.view

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.SurfaceHolder
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

class YoutubePlayer(private val context: Context): SurfaceHolder.Callback {

    private var mediaPlayer: ExoPlayer? = null
    var youtubePlayerListener: YoutubePlayerListener? = null

    override fun surfaceCreated(holder: SurfaceHolder) {
        if (mediaPlayer == null){
            mediaPlayer = ExoPlayer.Builder(context).build()
            mediaPlayer?.setVideoSurfaceHolder(holder)
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        release()
    }

    fun setUrl(url: String){
        mediaPlayer?.let {
            val mediaItem = MediaItem.fromUri(Uri.parse(url))
            it.setMediaItem(mediaItem)
            it.prepare()

            it.addListener(object : Player.Listener{
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    if(isPlaying) trackTime()
                }
            })

            play()
        }
    }

    private fun trackTime(){
        mediaPlayer?.apply {
            val currentPositionPercentage = currentPosition * 100 / duration
            youtubePlayerListener?.onTrackTime(currentPositionPercentage)
            if(isPlaying){
                Handler(Looper.getMainLooper()).postDelayed({
                    trackTime()
                }, 100)
            }
        }
    }

    private fun play(){
        mediaPlayer?.play()
    }

    fun pause(){
        mediaPlayer?.pause()
    }

    fun release(){
        mediaPlayer?.release()
    }

    interface YoutubePlayerListener{
        fun onPrepared(duration: Int)
        fun onTrackTime(currentPosition: Long)
    }

}