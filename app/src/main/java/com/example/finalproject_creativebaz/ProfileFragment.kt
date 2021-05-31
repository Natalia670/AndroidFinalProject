package com.example.finalproject_creativebaz

import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.finalproject_creativebaz.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private val user = Firebase.auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("users")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addProductButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_perfil_to_subirProducto)
        )
        edit_profile_btn.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_perfil_to_editProfileFragment)
        )

        home_btn.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_perfil_to_home_fragment)
        )

        getUserData()
        logout()
    }

    private fun getUserData(){
        val  userId = user.uid

        FirebaseAuth.getInstance().currentUser?.let { currentUser ->
            val name: String? = currentUser.displayName
            val profilePic : Uri? = currentUser.photoUrl
            profile_name.text = name.toString()
            Picasso.with(activity?.baseContext).load(profilePic.toString()).into(profile_picture)
        }?: kotlin.run {
            Log.d("user", "User not found")
        }

        reference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("user", "Got snapshot $snapshot")
                var userFound = false
                for (child in snapshot.children){
                    Log.d("user", "Inside For")
                    val objeto = child.getValue(UserExtraInfo::class.java)
                    if(userId == objeto?.userId){
                        if (!objeto.bio.isNullOrBlank() || !objeto.bio.isNullOrEmpty()){
                            Log.d("bio", "Resultado de objeto.bio = ${objeto.bio}")
                            bioTextView.text = objeto.bio
                        }else{
                            bioTextView.text = getString(R.string.bio_empty)
                        }
                        if(!objeto.profession.isNullOrEmpty()){
                            profession_text.text = objeto.profession
                        }else{
                            profession_text.text = getString(R.string.profession)
                        }
                        if(!objeto.contact.isNullOrEmpty()){
                            contact_text.text = objeto.contact
                        }else{
                            contact_text.text = getString(R.string.contact_example)
                        }
                        userFound = true
                        break
                    }
                }
                if(!userFound){
                    createUserInfo(userId)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        })
    }

    private fun createUserInfo(userId: String){
        val bio = getString(R.string.bio_empty)
        val contact = getString(R.string.contact_example)
        val profession = "Creador"

        val id = reference.push().key
        val userExtraInfo = UserExtraInfo(userId, profession, bio, contact)
        reference.child(id!!).setValue(userExtraInfo).addOnCompleteListener {
            Log.d("user", "Se creo la información del usuario")
        }
    }

    private fun logout(){
        logout_btn.setOnClickListener{
            Firebase.auth.signOut()
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }
    }

}