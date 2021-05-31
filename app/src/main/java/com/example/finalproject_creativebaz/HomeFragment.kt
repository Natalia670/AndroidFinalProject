package com.example.finalproject_creativebaz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_home.*
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        clothes_button.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_home_fragment_to_productFragment)
        )

        pictures_button.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_catalogo)
        )

        profileButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_perfil)
        )

        cartButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_home_fragment_to_cartFragment)
        )

        /*clothes_button.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_catalogo)
        }
        crafts_button.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_catalogo)
        }*/
    }
}

