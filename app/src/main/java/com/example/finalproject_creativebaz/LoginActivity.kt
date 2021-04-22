package com.example.finalproject_creativebaz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.finalproject_creativebaz.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var bind : ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Para Binding con elementos del Layout
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)

        // Inicializa objetos:
        auth = Firebase.auth
        setLoginRegister() //sigue en la siguiente sección.
    }

    private fun setLoginRegister(){
        bind.registerbtn.setOnClickListener {
            if (bind.mail.text.isNotEmpty() && bind.password.text.isNotEmpty()){
                // utiliza la clase de FirebaseAuth:
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(

                    bind.mail.text.toString(), //usuario y password
                    bind.password.text.toString()

                ).addOnCompleteListener{
                    if(it.isSuccessful){
                        userCreated() //Viene más adelante la función
                    }
                }.addOnFailureListener{
                    // en caso de error
                    Toast.makeText(this,it.toString(), Toast.LENGTH_LONG).show()

                }
            }
        }
        bind.loginbtn.setOnClickListener {
            // Valida que correo y password no esten vacíos, incluye:
            if(bind.mail.text.isNotEmpty() && bind.password.text.isNotBlank()){
                //Para ingresar cambia al método de signInWithEmailAndPassword
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    bind.mail.text.toString(),
                    bind.password.text.toString()
                ).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this,"Bienvenido a CreativeBaz!", Toast.LENGTH_LONG).show()
                        //Crea un intento y entra a MainActivity.
                        val intento = Intent(this, MainActivity::class.java)
                        startActivity(intento)
                        finish()

                    }else{
                        Toast.makeText(this,"Error en los datos!", Toast.LENGTH_LONG).show()
                    }
                }
            }
            else{
                Toast.makeText(this,"Llena los campos de correo y/o contraseña!", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun userCreated(){
        Toast.makeText(this,"Usuario creado exitosamente", Toast.LENGTH_LONG).show()
        bind.mail.text.clear() //Limpiar las cajas de texto
        bind.password.text.clear()
        val intento = Intent(this, MainActivity::class.java)
        startActivity(intento)
        finish()
    }
}