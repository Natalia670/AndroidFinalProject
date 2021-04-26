package com.example.finalproject_creativebaz

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.compose.runtime.currentRecomposeScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_upload_product.*


class EditProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private val user = Firebase.auth.currentUser
    private lateinit var profession: String

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
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        aceptar_btn.setOnClickListener {
            saveChangesProfile()
            //Navigation.createNavigateOnClickListener(R.id.action_editProfile_to_perfil)
            view?.findNavController()?.navigate(R.id.action_editProfile_to_perfil)
        }
        getUserData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner = view?.findViewById<Spinner>(R.id.profession_spinner)
        val lista = resources.getStringArray(R.array.professions)
        val adaptador = activity?.let{
            ArrayAdapter(it,android.R.layout.simple_spinner_item, lista)
        }

        spinner.adapter = adaptador
        spinner.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                profession = lista[position].toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                profession = lista[0].toString()
            }
        }

        //getUserData()
    }


    private fun getUserData(){
        val userId = user.uid

        FirebaseAuth.getInstance().currentUser?.let { currentUser ->
            val name: String? = currentUser.displayName
            val email: String? = currentUser.email
            edit_name.hint = name.toString()
            edit_mail.hint = email.toString()
        }?: kotlin.run {
            Log.d("user", "User not found")
        }

        reference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("user", "Got snapshot $snapshot")

                for (child in snapshot.children){
                    val objeto = child.getValue(UserExtraInfo::class.java)
                    if(userId == objeto?.userId){
                        if (!objeto.bio.isNullOrEmpty()){
                            edit_bio.hint = objeto.bio
                        }else{
                            edit_bio.hint = getString(R.string.bio_empty)
                        }
                        /*if(!objeto.profession.isNullOrEmpty()){
                            profession_text.text = objeto.profession
                        }else{
                            profession_text.text = getString(R.string.profession)
                        }*/
                        if(!objeto.contact.isNullOrEmpty()){
                            edit_contact.hint = objeto.contact
                        }else{
                            edit_contact.hint = getString(R.string.contact_example)
                        }
                        break
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        })
    }



    private fun saveChangesProfile(){

        if(!edit_name.text.isNullOrEmpty() || !edit_name.text.isNullOrBlank()){
            val profileUpdates = userProfileChangeRequest {
                displayName = edit_name.text.toString()
            }

            user!!.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("nameUpdate", "User profile updated.")
                        }
                    }
        }

        if(!edit_mail.text.isNullOrBlank() || !edit_mail.text.isNullOrEmpty()){
            user!!.updateEmail(edit_mail.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("emailUpdate", "User email address updated.")
                        }
                    }
        }

        if(!edit_bio.text.isNullOrBlank() || !edit_bio.text.isNullOrEmpty()){
            val id = reference.push().key

            val bioUpdates = hashMapOf<String, Any>(
                    "bio" to edit_bio.text.toString()
            )
            reference.updateChildren(bioUpdates).addOnCompleteListener {
                Log.d("user", "Se actualizó la información del usuario")
            }
        }


    }

}