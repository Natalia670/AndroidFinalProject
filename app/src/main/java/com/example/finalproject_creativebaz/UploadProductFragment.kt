package com.example.finalproject_creativebaz

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.view.get
import androidx.navigation.Navigation
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_upload_product.*
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.jar.Manifest

class UploadProductFragment : Fragment(){
    private var REQUEST_IMAGE_CAPTURE = 1
    private lateinit var foto: Bitmap
    private lateinit var categoria: String
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var analytics: FirebaseAnalytics
    private lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("products")
        bundle = Bundle()
        return inflater.inflate(R.layout.fragment_upload_product, container, false)

    }

    //inicializa el spinner
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner = view.findViewById<Spinner>(R.id.categoria)
        val lista = resources.getStringArray(R.array.categorias)
        val adaptador = activity?.let{
            ArrayAdapter(it,android.R.layout.simple_spinner_item, lista)
        }
        spinner.adapter = adaptador
        spinner.onItemSelectedListener = object:
        AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                categoria = lista[position].toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                categoria = lista[0].toString()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        UploadImage.setOnClickListener{
            fotoProducto()
        }
        PublishProduct.setOnClickListener{
            addProduct()
        }
    }

    public fun fotoProducto(){
        val tomaFoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(tomaFoto, REQUEST_IMAGE_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            foto = data?.extras?.get("data") as Bitmap
        }

    }

    public fun addProduct(){
        val user = Firebase.auth.currentUser
        val titulo = view?.findViewById<EditText>(R.id.editTextTextPersonName)?.text
        val creador = user.displayName
        //val creadorID = user.uid
        val descripcion= view?.findViewById<EditText>(R.id.descripcion)?.text
        val precio = view?.findViewById<EditText>(R.id.precio)?.text
        if(titulo!!.isNotEmpty() && titulo!!.isNotBlank() && categoria.isNotEmpty() && categoria.isNotBlank()){
            if( foto != null){
                // Convertir a bytes la foto
                val baos = ByteArrayOutputStream()
                foto.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val fileName = UUID.randomUUID().toString()
                val storage_reference = FirebaseStorage.getInstance().getReference("/productos/$fileName")
                val uploadTask = storage_reference.putBytes(data)
                uploadTask.addOnSuccessListener {
                    storage_reference.downloadUrl.addOnSuccessListener {
                        val id = reference.push().key
                        val product = Product(
                                id.toString(),
                                it.toString(),
                                titulo.toString(),
                                creador.toString(),
                                descripcion.toString(),
                                categoria,
                                precio.toString().toInt()
                        )
                        reference.child(id!!).setValue(product)
                        titulo.clear()
                        descripcion!!.clear()
                    }
                }.addOnFailureListener{
                    Toast.makeText(context, "Error al subir un producto", Toast.LENGTH_LONG).show()
                }
            }
            bundle.putString("tag", "added_product")
        }else{
            Toast.makeText(context, "Producto agregado éxitosamente", Toast.LENGTH_LONG).show()
        }
    }


}