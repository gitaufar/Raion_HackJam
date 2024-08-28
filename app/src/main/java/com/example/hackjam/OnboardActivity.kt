package com.example.hackjam

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.ViewPager
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class OnboardActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var frameList: List<Int>
    private lateinit var dotsIndicator: DotsIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboard)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        dotsIndicator = findViewById(R.id.dotsIndicator)
        viewPager = findViewById(R.id.idViewPager)
        val constraintLayout: ConstraintLayout = findViewById(R.id.swipe)
        constraintLayout.setOnTouchListener(ConstraintLayoutTouchListener(this,viewPager))

        // Initialize image list
        frameList = ArrayList()
        frameList = frameList + R.layout.onboard_1
        frameList = frameList + R.layout.onboard_2
        frameList = frameList + R.layout.onboard_3

        // Initialize view pager adapter
        viewPagerAdapter = ViewPagerAdapter(this, frameList)
        viewPager.adapter = viewPagerAdapter
        dotsIndicator.attachTo(viewPager)

    }
}
