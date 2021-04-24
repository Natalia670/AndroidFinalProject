package com.example.finalproject_creativebaz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_catalogue.*
import kotlinx.android.synthetic.main.product_preview.*


class CatalogueFragment : Fragment() {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var listProducts: ArrayList<Product>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalogue, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        products_recycler.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = CatalogueAdapter(createData())
        }
    }

    public fun getProduct(){
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listProducts = ArrayList<Product>()
                for (product in snapshot.children){
                    var objeto = product.getValue(Product::class.java)
                    listProducts.add(objeto as Product)
                }

                Log.i("Products", listProducts.toString())
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun createData(): ArrayList<Product>{
        getProduct()
        /*for(product in listProducts){
            product.add(Product)
        }
        products.add(Product(1,R.drawable,"Calectines de lana", "Florentina Velazco","Calientitos. Tejidos con amor y bordados a mano", "Ropa", 280))
        products.add(Product(2,R.drawable.img2, "Muñeca tradicional", "Romero Fosas", " Hecha de algodón y estambre. 15 cm de alto. Color del vestido: morado y rosa." ,"Artesanías", 300))
        products.add(Product(3,R.drawable.img3, "Bolsa reciclada", "Sócrates Gutiérrez", "Hecha con materiales reciclados. Color: dorado. Reforzada con acrílico", "Artesanías", 440))
        products.add(Product(4,R.drawable.img4,"Juego de tasas al estilo de Puebla", "Gabriela Pérez","Pintados por mi mamá y yo. Mi papá trabaja el barro y el diseño", "Artesanías", 900))*/
        return listProducts
    }
}