package com.example.taller1destinos.logica

import android.content.Intent
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
import com.example.taller1destinos.datos.Destino

class PantallaFavoritos : AppCompatActivity() {
    private val TAG = "PantallaExplorar" // Define a clear TAG for logging

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_explorar)

        Log.d(TAG, "Entró a la pantalla Explorar") // Log entry for entering the activity

        // Variables
        val txtCategoria = findViewById<TextView>(R.id.txtCategoria)
        val bolsaRecibida = intent.getBundleExtra("bolsaCategoria")
        val filtroCategoria: String

        //setLista()
    }

    /*fun setLista(){
        val lista = findViewById<ListView>(R.id.listDestinos)
        var nombresDestinos = ArrayList<String>()
        nombresDestinos = Data.FAVORITOS_LIST.map { it.destino }.map { it.nombre } as ArrayList<String>
        Log.e("PantallaFavoritosTAG", "Nombres de destinos: $nombresDestinos")

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            nombresDestinos
        )
        lista.adapter = adaptador

        lista.setOnItemClickListener{parent, view, position, id -> val destino = Data.DESTINOS_LIST[position]
            val intent = Intent(this, PantallaDetalles::class.java)
            val bolsaDestino = Bundle()
            bolsaDestino.putInt("id", destino.id)
            intent.putExtra("bolsaDestino", bolsaDestino)
            startActivity(intent)
        }
    }*/
}