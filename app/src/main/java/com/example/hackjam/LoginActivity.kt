package com.example.hackjam

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hackjam.databinding.ActivityLoginBinding
import com.example.hackjam.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue


class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val auth = FirebaseAuth.getInstance()
        val database = Firebase.database
        setContentView(binding.root)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        binding.signin.setOnClickListener(){
            Intent(this,SignActivity::class.java).also{
                startActivity(it)
            }
        }

        binding.showPass.setOnClickListener {
            togglePasswordVisibility(binding.pass)
        }

        binding.masukBtn.setOnClickListener(){
            var email: String = binding.username.text.toString()
            val pass: String = binding.pass.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty()){
                try{
                    val ref = Firebase.database.getReference("userList")
                    ref.child(email).get().addOnSuccessListener {
                        if(it.exists()){
                            email = it.getValue().toString()
                            auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(OnCompleteListener{
                                if(it.isSuccessful){
                                    if(!binding.swtc.isChecked){
                                        auth.signOut()
                                    }
                                    Intent(this,MainActivity::class.java).also{
                                        startActivity(it)
                                    }
                                } else {
                                    Toast.makeText(this, "wrong email or password", Toast.LENGTH_SHORT).show()
                                }
                            })

                        } else {
                            Toast.makeText(this, "username does'nt exist", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception){
                    auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(OnCompleteListener{
                        if(it.isSuccessful){
                            if(!binding.swtc.isChecked){
                                auth.signOut()
                            }
                            Intent(this,MainActivity::class.java).also{
                                startActivity(it)
                            }
                        } else {
                            Toast.makeText(this, "wrong email or password", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            } else {
                Toast.makeText(this, "email and password cant be empty", Toast.LENGTH_SHORT).show()
            }
        }

        }
    private fun togglePasswordVisibility(editText: EditText) {
        val selectionStart = editText.selectionStart
        val selectionEnd = editText.selectionEnd

        if (editText.transformationMethod == PasswordTransformationMethod.getInstance()) {
            editText.transformationMethod = null
            editText.setSelection(selectionStart, selectionEnd)
        } else {
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            editText.setSelection(selectionStart, selectionEnd)
        }
    }
    }