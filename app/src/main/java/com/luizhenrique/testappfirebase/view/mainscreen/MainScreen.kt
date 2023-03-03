package com.luizhenrique.testappfirebase.view.mainscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.luizhenrique.testappfirebase.R
import com.luizhenrique.testappfirebase.databinding.ActivityMainScreenBinding
import com.luizhenrique.testappfirebase.view.formlogin.FormLogin

class MainScreen : AppCompatActivity() {

    private lateinit var binding: ActivityMainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val backScreenLogin = Intent(this, FormLogin::class.java)
            startActivity(backScreenLogin)
            finish()
        }
    }
}