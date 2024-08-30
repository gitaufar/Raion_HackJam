package com.example.hackjam

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(HomeFragment())
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        if (savedInstanceState == null) {
            val fragmentToOpen = intent.getStringExtra("FRAGMENT_TO_OPEN")

            val botnav: BottomNavigationView = findViewById(R.id.bottomNavigationView)
            val fragment = when (fragmentToOpen) {
                "DiagnosisFragment" -> {
                    botnav.selectedItemId = R.id.bottom_diagnosis
                    DiagnosisFragment()
                }
                else -> HomeFragment() // Fragment default
            }

            replaceFragment(fragment)
        }

        FirebaseAuth.getInstance().signOut()

        val botnav: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        botnav.setOnItemSelectedListener { it ->
            when (it.itemId) {

                R.id.bottom_home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.bottom_lapor -> {
                    replaceFragment(LaporFragment())
                    true
                }

                R.id.bottom_komunitas -> {
                    replaceFragment(KomunitasFragment())
                    true
                }

                R.id.bottom_diagnosis -> {
                    replaceFragment(DiagnosisFragment())
                    true
                }

                else -> false
            }

        }


        }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_continer, fragment)
        fragmentTransaction.commit()
    }
    }