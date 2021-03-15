package com.example.finalproject_creativebaz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class CatalogueAdapter(private val products:List<Product>) : RecyclerView.Adapter<CatalogueAdapter.ProductViewHolder>(){

    inner class ProductViewHolder(row: View) : RecyclerView.ViewHolder(row){
        var picture = row.findViewById<ImageView>(R.id.imagen)
        var title = row.findViewById<TextView>(R.id.titulo)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val row = inflater.inflate(R.layout.product_preview,parent, false)
        return ProductViewHolder(row)
    }

    //Asocia datos con los elementos del renglón
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product= products[position]
        holder.picture.setImageResource(product.picture)
        holder.title.text = product.title
        holder.itemView.setOnClickListener {
            val action = CatalogueFragmentDirections.actionCatalogoToProducto(product)
            holder.itemView.findNavController().navigate(action)
        }
    }

    // Cuantos elementos tiene la lista
    override fun getItemCount(): Int {
        return products.size
    }
}