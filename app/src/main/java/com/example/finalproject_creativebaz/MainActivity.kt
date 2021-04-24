package com.example.finalproject_creativebaz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val catalogueFragment = CatalogueFragment()
        val notificationFragment = NotificationsFragment()
        val profileFragment = ProfileFragment()
        //makeCurrentFragment(homeFragment)

        /*bottom_navigation.setupWithNavController(findNavController(R.id.navHost))

        var appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf (
                    R.id.home_fragment,
                    R.id.catalogo,
                    R.id.producto
            )
        )
        setupActionBarWithNavController(findNavController(R.id.navHost), appBarConfiguration)*/


        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_search -> makeCurrentFragment(catalogueFragment)
                R.id.ic_notificactions -> makeCurrentFragment(notificationFragment)
                R.id.ic_profile -> makeCurrentFragment(profileFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}