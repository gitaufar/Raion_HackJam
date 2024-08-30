package com.example.hackjam

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.hackjam.databinding.ActivityLoginBinding
import com.example.hackjam.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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

        binding.username.addTextChangedListener(textWatcher)
        binding.pass.addTextChangedListener(textWatcher)

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
                                    val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                                    sharedPreferences.edit().putString("UID", auth.currentUser?.uid!!).apply()
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

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            // Ubah warna tombol berdasarkan teks yang diinputkan
            val isInputNotEmpty = binding.username.text.toString()
                .isNotEmpty() || binding.pass.text.toString().isNotEmpty()
            if (isInputNotEmpty) {
                binding.masukBtn.setBackgroundResource(R.drawable.btn_bg_after)
                binding.masukBtn.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.white))
            } else {
                binding.masukBtn.setBackgroundResource(R.drawable.btn_bg_before)
                binding.masukBtn.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.grey))
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