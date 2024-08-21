package com.example.youtube.feature.main.view

import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout

class MotionTransition(private val view: View): MotionLayout.TransitionListener {

    override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}

    override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {
        if (progress > 0.5f){
            view.alpha = 1.0f - progress
        } else{
            view.alpha = 0.5f
        }
    }

    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {}

    override fun onTransitionTrigger(motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) {}

}