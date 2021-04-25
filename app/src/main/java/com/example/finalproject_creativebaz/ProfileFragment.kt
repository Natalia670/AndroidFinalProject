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
import androidx.navigation.Navigation
import com.example.finalproject_creativebaz.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    private lateinit var bind : ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        getUserData()
        logout()

        /*clothes_button.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_catalogo)
        }
        crafts_button.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_catalogo)
        }*/
    }

    private fun getUserData(){
        FirebaseAuth.getInstance().currentUser?.let { currentUser ->
            // the user is already logged in, so get the details from him/her

            // uid is unique for every logged in user, and wil always be not-null
            val uid : String = currentUser.uid
            // rest of the parameters might be null depending on the auth provider used
            val name: String? = currentUser.displayName
            val email: String? = currentUser.email
            val profilePic : Uri? = currentUser.photoUrl
            //val contactNumber = currentUser.phoneNumber

            Log.d("userD", uid)
            if (name != null) {
                Log.d("userD", name)
            }

            Log.d("userD", email.toString())
            Log.d("userD", profilePic.toString())

            profile_name.text = name.toString()
            Picasso.with(activity?.baseContext).load(profilePic.toString()).into(profile_picture)


        }?: kotlin.run {
            // currentUser is null, that means the user is not logged in yet!
            Log.d("user", "User not found")
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