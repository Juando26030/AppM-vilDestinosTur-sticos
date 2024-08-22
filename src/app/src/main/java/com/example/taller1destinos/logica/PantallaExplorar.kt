package com.example.taller1destinos.logica

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taller1destinos.R
import com.example.taller1destinos.datos.Data

class PantallaExplorar : AppCompatActivity() {
    private val TAG = "PantallaExplorar" // Define a clear TAG for logging

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_explorar)

        Log.d(TAG, "Entró a la pantalla Explorar") // Log entry for entering the activity

        // Variables
        val txtCategoria = findViewById<TextView>(R.id.txtCategoria)
        val bolsaRecibida = intent.getBundleExtra("bolsaCategoria")
        val filtroCategoria: String

        if (bolsaRecibida != null) {
            filtroCategoria = bolsaRecibida.getString("categoria")?: ""
            Log.i(TAG, "Valor en la bolsa (categoria): $filtroCategoria") // Log entry with category value (if available)
            txtCategoria.text = "Filtrando por: " + filtroCategoria
            setLista(filtroCategoria)
        } else {
            Log.w(TAG, "No se encontró la bolsa 'bolsaCategoria' en el intent") // Log entry if the bundle is missing
            txtCategoria.text = "Categoría no disponible"
        }
    }

    fun setLista(filtro: String){
        val lista = findViewById<ListView>(R.id.listDestinos)
        var nombresDestinos = ArrayList<String>()
        if(filtro.equals("Todos")){
            nombresDestinos = Data.DESTINOS_LIST.map { it.nombre } as ArrayList<String>
        }else{
            nombresDestinos = Data.DESTINOS_LIST.filter { it.categoria == filtro }.map { it.nombre } as ArrayList<String>
        }
        Log.e("PantallaFavoritosTAG", "Nombres de destinos: $nombresDestinos")

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            nombresDestinos
        )
        lista.adapter = adaptador

        lista.setOnItemClickListener(object: AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(baseContext, position.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}