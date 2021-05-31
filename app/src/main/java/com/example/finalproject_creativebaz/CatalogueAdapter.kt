package com.example.finalproject_creativebaz

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.io.InputStream
import java.net.URL


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

        Log.i("Products", product.toString())
        Picasso.with(holder.picture.context).load(product.picture).into(holder.picture);
        holder.title.text = product.title
        holder.itemView.setOnClickListener {
            val action = CatalogueFragmentDirections.actionCatalogoToProductFragment()
            holder.itemView.findNavController().navigate(action)
        }
    }

    // Cuantos elementos tiene la lista
    override fun getItemCount(): Int {
        return products.size
    }
}