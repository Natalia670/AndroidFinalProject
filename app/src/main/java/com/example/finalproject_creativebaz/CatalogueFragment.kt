package com.example.finalproject_creativebaz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_catalogue.*
import kotlinx.android.synthetic.main.product_preview.*


class CatalogueFragment : Fragment() {
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalogue, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("products")
        getProducts()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        button_pintura.setOnClickListener (
            //Log.i("Button Product Detail", "Send me to the next view")
            //view?.findNavController()?.navigate(R.id.action_catalogo_to_productFragment)
            Navigation.createNavigateOnClickListener(R.id.action_catalogo_to_productFragment)
        )

        /*button_pintura.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_catalogo_to_productFragment)
        )*/



    }

    fun getProducts(){

         /*reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var pl = ArrayList<Product>()
                for (product in snapshot.children){
                    var obj = product.getValue(Product::class.java)
                    pl.add(obj as Product)
                }
                products_recycler.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CatalogueAdapter(pl)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })*/

    }


    /*fun createData() : ArrayList<Product>{


        for(product in listProducts){
            product.add(Product)
        }
        products.add(Product(1,R.drawable,"Calectines de lana", "Florentina Velazco","Calientitos. Tejidos con amor y bordados a mano", "Ropa", 280))
        products.add(Product(2,R.drawable.img2, "Mu??eca tradicional", "Romero Fosas", " Hecha de algod??n y estambre. 15 cm de alto. Color del vestido: morado y rosa." ,"Artesan??as", 300))
        products.add(Product(3,R.drawable.img3, "Bolsa reciclada", "S??crates Guti??rrez", "Hecha con materiales reciclados. Color: dorado. Reforzada con acr??lico", "Artesan??as", 440))
        products.add(Product(4,R.drawable.img4,"Juego de tasas al estilo de Puebla", "Gabriela P??rez","Pintados por mi mam?? y yo. Mi pap?? trabaja el barro y el dise??o", "Artesan??as", 900))
    }*/
}