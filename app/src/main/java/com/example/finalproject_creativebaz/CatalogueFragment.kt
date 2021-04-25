package com.example.finalproject_creativebaz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    fun getProducts(){

         reference.addValueEventListener(object : ValueEventListener {
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

        })

    }


    /*fun createData() : ArrayList<Product>{


        for(product in listProducts){
            product.add(Product)
        }
        products.add(Product(1,R.drawable,"Calectines de lana", "Florentina Velazco","Calientitos. Tejidos con amor y bordados a mano", "Ropa", 280))
        products.add(Product(2,R.drawable.img2, "Muñeca tradicional", "Romero Fosas", " Hecha de algodón y estambre. 15 cm de alto. Color del vestido: morado y rosa." ,"Artesanías", 300))
        products.add(Product(3,R.drawable.img3, "Bolsa reciclada", "Sócrates Gutiérrez", "Hecha con materiales reciclados. Color: dorado. Reforzada con acrílico", "Artesanías", 440))
        products.add(Product(4,R.drawable.img4,"Juego de tasas al estilo de Puebla", "Gabriela Pérez","Pintados por mi mamá y yo. Mi papá trabaja el barro y el diseño", "Artesanías", 900))
    }*/
}