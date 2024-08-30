package com.example.hackjam

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hackjam.databinding.ActivityResultBinding
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private var typingJob: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        setContentView(binding.root)
        val database = Firebase.database
        binding.backHome.setOnClickListener() {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
        val progress = intent.getIntExtra("TOTAL_POIN", 0)
        val text = arrayOf(
            "Berdasarkan data kamu tidak pernah terkena pelecehan :D",
            "Berdasarkan data di atas kemungkinan kamu terkena pelecehan silahkan konsultasi ke komunitas terdekat yang sudah kami sediakan.",
            "Berdasarkan data diatas, kamu terdiagnosis pernah mengalami pelecehan seksual tanpa kamu sadari. Ini bukanlah salahmu,  dan kamu tidak sendirian. Coba berdamailah dengan dirimu sendiri karena badai ini akan terlewat suatu saat nanti. Kami menyediakan beberapa opsi untuk kamu dapat meminta pertolongan kepada pihak yang berwajib."
        )
        when {
            progress <= 80 -> {
                binding.textHasil.text = text[0]
            }

            progress <= 200 -> {
                binding.textHasil.text = text[1]
            }

            else -> {
                binding.textHasil.text = text[2]
            }
        }
        animateProgressBar(
            progress,
            binding.bar,
            binding.persentage,
            binding.persen,
            binding.flProgress,
            binding.hasilDiagnosis
        )
        val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val uid = sharedPreferences.getString("UID", null)
        val ref = database.getReference("users")
        if (uid != null) {
            val ref = Firebase.database.getReference("users")
            ref.child(uid).child("username").get()
                .addOnSuccessListener {
                    binding.name.text = "Halo, ${it.value}"
                }
        } else {
            Toast.makeText(this, "User not found, please login again.", Toast.LENGTH_SHORT).show()
            // Arahkan kembali ke login jika UID tidak ditemukan
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }

        }
    }

    private fun animateProgressBar(
        max: Int,
        bar: CircularProgressIndicator,
        angka: TextView,
        persen: TextView,
        container: FrameLayout,
        hasil: ConstraintLayout
    ) {

        val FPS = 60
        val drawInterval = (1000 / FPS).toLong()
        typingJob = GlobalScope.launch(Dispatchers.Main) {
            var red = 0
            var green = 255
            val blue = 0
            for (i in 0..max) {
                red = (red + 3).coerceAtMost(255)
                if (red == 255) {
                    green = (green - 2).coerceAtLeast(0)
                }
                angka.text = (i / 4).toString();
                angka.setTextColor(android.graphics.Color.rgb(red, green, blue))
                persen.setTextColor(android.graphics.Color.rgb(red, green, blue))
                bar.setIndicatorColor(android.graphics.Color.rgb(red, green, blue))
                bar.progress = i
                delay(drawInterval)
            }
            upIcon(angka, persen, bar, container)
            delay(300)
            upHasil(hasil)
        }
    }

    fun upHasil(hasil: ConstraintLayout) {
        hasil.visibility = View.VISIBLE
        val scaleY = ObjectAnimator.ofFloat(hasil, "scaleY", 0f, 1f)
        scaleY.setDuration(300)
        val animatorSet = AnimatorSet()
        animatorSet.play(scaleY)
        animatorSet.start()
    }

    fun upIcon(
        number: TextView,
        persen: TextView,
        bar: CircularProgressIndicator,
        container: FrameLayout
    ) {

        number.pivotX = 0f
        number.pivotY = (number.height / 2).toFloat()

        persen.pivotX = 0f
        persen.pivotY = (persen.height / 2).toFloat()

        val scaleX = ObjectAnimator.ofFloat(number, "scaleX", 1f, 0.65f)
        val scaleY = ObjectAnimator.ofFloat(number, "scaleY", 1f, 0.65f)
        val scaleX2 = ObjectAnimator.ofFloat(persen, "scaleX", 1f, 0.65f)
        val scaleY2 = ObjectAnimator.ofFloat(persen, "scaleY", 1f, 0.65f)
        val scaleX3 = ObjectAnimator.ofFloat(bar, "scaleX", 1f, 0.65f)
        val scaleY3 = ObjectAnimator.ofFloat(bar, "scaleY", 1f, 0.65f)
        val translationY2 = ObjectAnimator.ofFloat(container, "translationY", 0f, -300f)

        val centerPersen =
            ObjectAnimator.ofFloat(persen, "translationY", 0f, -(persen.height * 0.07f))
        val centerNumberX =
            ObjectAnimator.ofFloat(number, "translationX", 0f, (number.width * 0.35f))

        val duration = 300L
        scaleX.duration = duration
        scaleY.duration = duration
        scaleX2.duration = duration
        scaleY2.duration = duration
        scaleX3.duration = duration
        scaleY3.duration = duration
        translationY2.duration = duration
        centerNumberX.duration = duration
        centerPersen.duration = duration

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            scaleX,
            scaleY,
            scaleX2,
            scaleY2,
            scaleX3,
            scaleY3,
            translationY2,
            centerNumberX,
            centerPersen
        )

        animatorSet.start()

    }
}