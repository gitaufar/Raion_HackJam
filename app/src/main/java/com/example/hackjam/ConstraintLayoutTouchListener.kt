package com.example.hackjam

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.viewpager.widget.ViewPager

class ConstraintLayoutTouchListener(
    private val activity: Activity,
    private val viewPager: ViewPager
) : View.OnTouchListener {

    companion object {
        private const val LOG_TAG = "ActivitySwipeDetector"
        private const val MIN_DISTANCE = 100 // TODO: Adjust runtime based on screen resolution
    }

    private var downX = 0f
    private var downY = 0f
    private var upX = 0f
    private var upY = 0f

    private fun onRightToLeftSwipe() {
        Log.i(LOG_TAG, "RightToLeftSwipe!")
        viewPager.currentItem = (viewPager.currentItem + 1) % viewPager.adapter?.count!!
    }

    private fun onLeftToRightSwipe() {
        Log.i(LOG_TAG, "LeftToRightSwipe!")
        viewPager.currentItem = (viewPager.currentItem - 1 + viewPager.adapter?.count!!) % viewPager.adapter?.count!!
    }

    private fun onTopToBottomSwipe() {
        Log.i(LOG_TAG, "onTopToBottomSwipe!")
        // Add your top-to-bottom swipe action here if needed
    }

    private fun onBottomToTopSwipe() {
        Log.i(LOG_TAG, "onBottomToTopSwipe!")
        val intent = Intent(activity, OpeningActivity::class.java)
        activity.startActivity(intent)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                return true
            }
            MotionEvent.ACTION_UP -> {
                upX = event.x
                upY = event.y

                val deltaX = downX - upX
                val deltaY = downY - upY

                // Swipe horizontal?
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    // Left or right
                    if (deltaX < 0) {
                        onLeftToRightSwipe()
                        return true
                    }
                    if (deltaX > 0) {
                        onRightToLeftSwipe()
                        return true
                    }
                } else {
                    Log.i(LOG_TAG, "Swipe was only ${Math.abs(deltaX)} long horizontally, need at least $MIN_DISTANCE")
                }

                // Swipe vertical?
                if (Math.abs(deltaY) > MIN_DISTANCE) {
                    // Top or down
                    if (deltaY < 0) {
                        onTopToBottomSwipe()
                        return true
                    }
                    if (deltaY > 0) {
                        onBottomToTopSwipe()
                        return true
                    }
                } else {
                    Log.i(LOG_TAG, "Swipe was only ${Math.abs(deltaY)} long vertically, need at least $MIN_DISTANCE")
                }

                return false // No swipe horizontally and no swipe vertically
            }
        }
        return false
    }
}
