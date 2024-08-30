package com.example.hackjam

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hackjam.databinding.ActivityLoginBinding
import com.example.hackjam.databinding.ActivitySignBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class SignActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        val database = Firebase.database
        setContentView(binding.root)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        binding.login.setOnClickListener() {
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.showPass.setOnClickListener {
            togglePasswordVisibility(binding.pass)
        }

        binding.showPass2.setOnClickListener(){
            togglePasswordVisibility(binding.confirmPass)
        }

        binding.username.addTextChangedListener(textWatcher)
        binding.pass.addTextChangedListener(textWatcher)
        binding.confirmPass.addTextChangedListener(textWatcher)
        binding.email.addTextChangedListener(textWatcher)

        binding.masukBtn.setOnClickListener() {
            val email = binding.email.text.toString()
            val pass = binding.pass.text.toString()
            val confirmPass = binding.confirmPass.text.toString()
            val username = binding.username.text.toString();
            val ref = database.getReference(username);
            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty() && username.isNotEmpty()) {
                val ref2 = database.getReference("data");
                ref.get().addOnSuccessListener {
                    if (it.exists()) {
                        Toast.makeText(
                            this,
                            "Username exist, please change",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (pass == confirmPass) {
                            firebaseAuth.createUserWithEmailAndPassword(email, pass)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val userId = firebaseAuth.currentUser?.uid
                                        val userRef = FirebaseDatabase.getInstance().reference.child("users").child(userId!!)
                                        val userNameRef = FirebaseDatabase.getInstance().reference.child("userList").child(username).setValue(email)

                                        userRef.child("username").setValue(username)
                                        userRef.child("email").setValue(email)
                                        val intent = Intent(this, LoginActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(
                                            this,
                                            task.exception?.message ?: "Registration failed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        } else {
                            Toast.makeText(
                                this,
                                "Password is not matching",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    "Empty Fields Are Not Allowed ",
                    Toast.LENGTH_SHORT
                ).show()
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
                binding.masukBtn.setTextColor(ContextCompat.getColor(this@SignActivity, R.color.white))
            } else {
                binding.masukBtn.setBackgroundResource(R.drawable.btn_bg_before)
                binding.masukBtn.setTextColor(ContextCompat.getColor(this@SignActivity, R.color.grey))
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