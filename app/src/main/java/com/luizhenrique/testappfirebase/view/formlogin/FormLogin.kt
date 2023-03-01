package com.luizhenrique.testappfirebase.view.formlogin

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.RED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.luizhenrique.testappfirebase.R
import com.luizhenrique.testappfirebase.databinding.ActivityFormLoginBinding
import com.luizhenrique.testappfirebase.view.formregister.FormRegister
import com.luizhenrique.testappfirebase.view.mainscreen.MainScreen

class FormLogin : AppCompatActivity() {
    private lateinit var binding: ActivityFormLoginBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {view ->

            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                val snackbar = Snackbar.make(view, "Preencha todos os campos.", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else{
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { authentication ->
                   if(authentication.isSuccessful){
                    navigationMainScreen()
                   }
                }.addOnFailureListener {exception ->

                    val messageError = when(exception){
                        is FirebaseAuthWeakPasswordException -> "Senha invalida."
                        is FirebaseAuthInvalidCredentialsException -> "Email não cadastrado,"
                        is FirebaseAuthUserCollisionException -> "Usuário já cadastrado."
                        is FirebaseNetworkException -> "Sem conexão com a internet."
                        else -> "Erro ao cadastrar usuário."
                    }
                    val snackbar = Snackbar.make(view, messageError, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }
        }

        binding.txtScreenRegister.setOnClickListener {
            val intent = Intent(this, FormRegister::class.java)
            startActivity(intent)
        }
    }

    private fun navigationMainScreen(){
        val intent = Intent(this, MainScreen::class.java)
        startActivity(intent)
    }
}