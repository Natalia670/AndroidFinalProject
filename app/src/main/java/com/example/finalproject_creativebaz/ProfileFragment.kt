package com.example.finalproject_creativebaz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

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


        /*clothes_button.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_catalogo)
        }
        crafts_button.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_catalogo)
        }*/
    }

}