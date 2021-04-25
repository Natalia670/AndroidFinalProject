package com.example.finalproject_creativebaz

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.finalproject_creativebaz.databinding.ActivityLoginBinding
import com.example.finalproject_creativebaz.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*
import java.math.BigInteger
import java.security.MessageDigest

class RegisterActivity : AppCompatActivity() {

    private lateinit var bind : ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Para Binding con elementos del Layout
        bind = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bind.root)

        // Inicializa objetos:
        auth = Firebase.auth
        setRegister() //sigue en la siguiente sección.
    }

    private fun  setRegister(){
        bind.registerbtn.setOnClickListener {
            if (bind.mail.text.isNotEmpty() && bind.password.text.isNotEmpty() && bind.name.text.isNotEmpty() ){
                // utiliza la clase de FirebaseAuth:
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    bind.mail.text.toString(), //usuario y password
                    bind.password.text.toString()
                ).addOnCompleteListener{
                    if(it.isSuccessful){

                        //Añadir campos al usuario

                        val profileUpdates = userProfileChangeRequest {
                            displayName = bind.name.text.toString()
                            val gravatarHash = md5(bind.mail.text.toString())
                            photoUri = Uri.parse("https://www.gravatar.com/avatar/${gravatarHash}?d=monsterid&f=y&r=pg")
                        }

                        auth.currentUser!!.updateProfile(profileUpdates).addOnCompleteListener { task ->
                           if (task.isSuccessful) {
                               Log.d("userD", "User Name and photo added successfully")
                           }
                        }

                        Toast.makeText(this,"Usuario creado exitosamente", Toast.LENGTH_LONG).show()
                        val intento = Intent(this, MainActivity::class.java)
                        startActivity(intento)
                        finish()//Viene más adelante la función
                    }
                }.addOnFailureListener{
                    // en caso de error
                    Toast.makeText(this,it.toString(), Toast.LENGTH_LONG).show()

                }
            }
        }
        bind.loginbtn.setOnClickListener(){
            val intento = Intent(this, LoginActivity::class.java)
            startActivity(intento)
            finish()
        }
    }

    fun md5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}