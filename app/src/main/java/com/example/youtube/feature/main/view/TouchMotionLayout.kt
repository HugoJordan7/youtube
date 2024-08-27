package com.example.youtube.feature.main.view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.youtube.R
import kotlin.math.abs

class TouchMotionLayout(context: Context, attributeSet: AttributeSet) :
    MotionLayout(context, attributeSet) {

    private val hidePlayer: ImageView by lazy {
        findViewById(R.id.hide_player)
    }

    private val imageBase: ImageView by lazy {
        findViewById(R.id.video_player)
    }

    private val playButton: ImageView by lazy {
        findViewById(R.id.play_button)
    }

    private val nextButton: ImageView by lazy {
        findViewById(R.id.next_button)
    }

    private val playlist: ImageView by lazy {
        findViewById(R.id.playlist_player)
    }

    private val fullPlayer: ImageView by lazy {
        findViewById(R.id.full_player)
    }

    private val sharePlayer: ImageView by lazy {
        findViewById(R.id.share_player)
    }

    private val morePlayer: ImageView by lazy {
        findViewById(R.id.more_player)
    }

    private val previousButton: ImageView by lazy {
        findViewById(R.id.previous_button)
    }

    private val seekBar: SeekBar by lazy {
        findViewById(R.id.seek_bar)
    }

    private val viewFrame: View by lazy {
        findViewById(R.id.video_layer)
    }

    private val currentTime: TextView by lazy {
        findViewById(R.id.current_time)
    }

    private val durationTime: TextView by lazy {
        findViewById(R.id.duration_time)
    }

    private val animateViewArray by lazy { arrayOf(
        playButton, hidePlayer, nextButton, previousButton, playlist,
        fullPlayer, sharePlayer, morePlayer, currentTime, durationTime
    ) }

    private var startX: Float? = null
    private var startY: Float? = null
    private var isPaused = false

    private lateinit var animFadeIn: AnimatorSet
    private lateinit var animFadeOut: AnimatorSet

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        val isInTarget = touchEventInsideTargetView(imageBase, event!!)
        val isInProgress = (progress > 0.0f && progress < .5f)

        return if (isInProgress || isInTarget) {
            super.onInterceptTouchEvent(event)
        } else {
            false
        }
    }

    private fun touchEventInsideTargetView(v: View, ev: MotionEvent): Boolean {
        if (ev.x > v.left && ev.x < v.right) {
            if (ev.y > v.top && ev.y < v.bottom) {
                return true
            }
        }
        return false
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.x
                startY = ev.y
            }

            MotionEvent.ACTION_UP -> {
                val endX = ev.x
                val endY = ev.y

                if (isAClick(startX!!, endX, startY!!, endY)) {
                    if (touchEventInsideTargetView(imageBase, ev)) {
                        if (doClick(imageBase)) {
                            return true
                        }
                    }
                }

            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isAClick(startX: Float, endX: Float, startY: Float, endY: Float): Boolean {
        val differenceX = abs(startX - endX)
        val differenceY = abs(startY - endY)
        return !(differenceX > 200 || differenceY > 200)
    }

    private fun doClick(view: View): Boolean {
        var isClickHandled = false

        if (progress < 0.05f) {
            isClickHandled = true

            when (view) {
                imageBase -> {
                    if (!isPaused) {
                        animateFade {
                            animFadeOut.startDelay = 1000
                            animFadeOut.start()
                        }
                    }
                }
            }
        }
        return isClickHandled
    }

    private fun animateFade(onAnimationEnd: () -> Unit) {
        animFadeOut = AnimatorSet()
        animFadeIn = AnimatorSet()

        fade(animFadeIn, animateViewArray, true)

        animFadeIn.play(
            ObjectAnimator.ofFloat(viewFrame, View.ALPHA, 0f, .5f)
        )

        val valueFadeIn = ValueAnimator.ofInt(0, 255)
            .apply {
                addUpdateListener {
                    seekBar.thumb.mutate().alpha = it.animatedValue as Int
                }
                duration = 200
            }

        animFadeIn.play(valueFadeIn)

        fade(animFadeOut, animateViewArray, false)

        val valueFadeOut = ValueAnimator.ofInt(255, 0)
            .apply {
                addUpdateListener {
                    seekBar.thumb.mutate().alpha = it.animatedValue as Int
                }
                duration = 200
            }

        animFadeOut.play(valueFadeOut)

        animFadeIn.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {}
            override fun onAnimationCancel(p0: Animator) {}
            override fun onAnimationRepeat(p0: Animator) {}
            override fun onAnimationEnd(p0: Animator) {
                onAnimationEnd.invoke()
            }
        })

        animFadeIn.start()
    }

    private fun fade(animatorSet: AnimatorSet, view: Array<View>, toZero: Boolean) {
        view.forEach {
            animatorSet.play(
                ObjectAnimator.ofFloat(
                    it, View.ALPHA,
                    if (toZero) 0f else 1f,
                    if (toZero) 1f else 0f
                )
            )
        }
    }

}