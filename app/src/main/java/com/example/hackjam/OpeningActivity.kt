package com.example.hackjam

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hackjam.databinding.ActivityLoginBinding
import com.example.hackjam.databinding.ActivityOpeningBinding
import com.example.hackjam.databinding.ActivitySplashBinding

class OpeningActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOpeningBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpeningBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        binding.loginBtn.setOnClickListener(){
            Intent(this,LoginActivity::class.java).also{
                startActivity(it)
            }
        }
        binding.signBtn.setOnClickListener(){
            Intent(this,SignActivity::class.java).also{
                startActivity(it)
            }
        }
    }
}