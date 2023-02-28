package com.luizhenrique.testappfirebase.view.formregister

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.luizhenrique.testappfirebase.R
import com.luizhenrique.testappfirebase.databinding.ActivityFormRegisterBinding

class FormRegister : AppCompatActivity() {

    private lateinit var binding: ActivityFormRegisterBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRegister.setOnClickListener {view ->
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                val snackbar = Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else{
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {register ->
                    if(register.isSuccessful){
                        val snackbar = Snackbar.make(view, "Sucesso ao cadastrar o usuário!", Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.GREEN)
                        snackbar.show()
                        binding.editEmail.setText("")
                        binding.editPassword.setText("")
                    }
                }.addOnFailureListener {exception ->

                    val messageError = when(exception){
                        is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo 6 caraceters."
                        is FirebaseAuthInvalidCredentialsException -> "Digite um email válido."
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
    }
}