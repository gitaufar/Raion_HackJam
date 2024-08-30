package com.example.hackjam

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hackjam.databinding.ActivityLoginBinding
import com.example.hackjam.databinding.ActivityQuestionBinding
import com.example.hackjam.fragment_soal.SoalFragment1
import com.example.hackjam.fragment_soal.SoalFragment10
import com.example.hackjam.fragment_soal.SoalFragment2
import com.example.hackjam.fragment_soal.SoalFragment3
import com.example.hackjam.fragment_soal.SoalFragment4
import com.example.hackjam.fragment_soal.SoalFragment5
import com.example.hackjam.fragment_soal.SoalFragment6
import com.example.hackjam.fragment_soal.SoalFragment7
import com.example.hackjam.fragment_soal.SoalFragment8
import com.example.hackjam.fragment_soal.SoalFragment9
import com.google.android.gms.maps.model.StyleSpan

class QuestionActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var binding: ActivityQuestionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = ViewModelProvider(this).get(QuestionActiviyViewModel::class.java)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        binding.lanjut.setOnClickListener(){
            viewModel.incrementQuestion()
        }

        binding.kembali.setOnClickListener(){
            viewModel.decrementQuestion()
        }

        binding.lewati.setOnClickListener(){
            viewModel.resetAnswer(viewModel.getQuestion() - 1)
            viewModel.incrementQuestion()
        }

        viewModel.question.observe(this, Observer { count ->

            binding.progress.progress = count
            if(count <= 10 && count > 0){
                binding.textPertanyaan.text = updateText(count)
            }

            when (count) {
                0 -> {
                    Intent(this, MainActivity::class.java).apply {
                        putExtra("FRAGMENT_TO_OPEN", "DiagnosisFragment")
                        startActivity(this)
                    }
                }
                1 -> replaceFragment(SoalFragment1())
                2 -> replaceFragment(SoalFragment2())
                3 -> replaceFragment(SoalFragment3())
                4 -> replaceFragment(SoalFragment4())
                5 -> replaceFragment(SoalFragment5())
                6 -> replaceFragment(SoalFragment6())
                7 -> replaceFragment(SoalFragment7())
                8 -> replaceFragment(SoalFragment8())
                9 -> replaceFragment(SoalFragment9())
                10 -> replaceFragment(SoalFragment10())

                else -> {
                    Intent(this, ResultActivity::class.java).apply {
                        putExtra("TOTAL_POIN", viewModel.countPoint())
                        startActivity(this)
                        finish()
                    }
                }

            }
        })

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_tv, fragment)
        fragmentTransaction.commit()
    }

    private fun updateText(count: Int): SpannableString {
        val fullText = "Pertanyaan $count dari 10"

        // Menemukan posisi angka pertama dan angka kedua
        val startBoldCount = fullText.indexOf(count.toString())
        val endBoldCount = startBoldCount + count.toString().length

        val startBoldTotal = fullText.indexOf("10")
        val endBoldTotal = startBoldTotal + "10".length

        val spannable = SpannableString(fullText)

        spannable.setSpan(StyleSpan(Typeface.BOLD), startBoldCount, endBoldCount, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(StyleSpan(Typeface.BOLD), startBoldTotal, endBoldTotal, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        return spannable
    }
}