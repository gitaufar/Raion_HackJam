package com.example.hackjam

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.hackjam.databinding.ActivitySignBinding
import com.example.hackjam.databinding.ActivitySplashBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding
    private lateinit var firebaseAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        firebaseAuth = FirebaseAuth.getInstance()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val handler = Handler(Looper.getMainLooper())
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.Main).launch {
            showIcon(binding.first)
            delay(400)
            showIcon(binding.second)
            delay(400)
            showIcon(binding.third)
            delay(400)
            showIcon(binding.four)
            delay(400)
            binding.logo.visibility = View.VISIBLE
            upIcon(binding.first,binding.second,binding.third,binding.four,binding.logo,binding.fl)
        }

        handler.postDelayed({
            if(firebaseAuth.currentUser != null){
                Intent(this,MainActivity::class.java).also{
                    startActivity(it)
                }
            } else {
                Intent(this,OnboardActivity::class.java).also{
                    startActivity(it)
                }
            }
        },2400)
    }

    fun showIcon(img: ImageView){
        img.visibility = View.VISIBLE
        val scaleX = ObjectAnimator.ofFloat(img, "scaleX", 0f, 1f)
        val scaleY = ObjectAnimator.ofFloat(img, "scaleY", 0f, 1f)
        val alpha = ObjectAnimator.ofFloat(img, "alpha", 0f, 1f)

        scaleX.setDuration(500)
        scaleY.setDuration(500)
        alpha.setDuration(500)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY, alpha)
        animatorSet.start()

    }

    fun upIcon(img1: ImageView, img2: ImageView,img3: ImageView,img4: ImageView,img: ImageView,fl: FrameLayout){
        img1.visibility = View.GONE
        img2.visibility = View.GONE
        img3.visibility = View.GONE
        img4.visibility = View.GONE

        val scaleX = ObjectAnimator.ofFloat(img, "scaleX", 1f, 0.65f)
        val scaleY = ObjectAnimator.ofFloat(img, "scaleY", 1f, 0.65f)
        val translationY = ObjectAnimator.ofFloat(img,"translationY",0f,-65f)
        val translationY2 = ObjectAnimator.ofFloat(fl,"translationY",0f,-200f)

        scaleX.setDuration(500)
        scaleY.setDuration(500)
        translationY.setDuration(500)
        translationY2.setDuration(500)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY, translationY,translationY2)
        animatorSet.start()

    }
}