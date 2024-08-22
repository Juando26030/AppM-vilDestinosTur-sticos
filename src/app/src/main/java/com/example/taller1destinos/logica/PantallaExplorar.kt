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
            val tipo = bolsaRecibida.getInt("tipo")
            if(tipo == 1){
                definirPantallas(filtroCategoria, 1)
            }else if(tipo == 2){
                definirPantallas(filtroCategoria, 2)
            }else if(tipo == 3){
                definirPantallas(filtroCategoria, 3)
            }
        } else {
            Log.w(TAG, "No se encontró la bolsa 'bolsaCategoria' en el intent") // Log entry if the bundle is missing
            txtCategoria.text = "Categoría no disponible"
        }
    }

    fun definirPantallas(filtro: String, tipo: Int){
        val lista = findViewById<ListView>(R.id.listDestinos)
        var nombresDestinos = ArrayList<String>()
        if(tipo == 1){
            nombresDestinos = setListaExplorar(filtro)
        }else if(tipo == 2){
            nombresDestinos = setListaFavoritos()
        }/*else if (tipo == 3){
            nombresDestinos = setListaRecomendaciones()
        }*/
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
            bolsaDestino.putInt("id", destino.id-1)
            if(tipo == 1){
                bolsaDestino.putInt("tipo", 1)
            }else{
                bolsaDestino.putInt("tipo", 2)
            }
            intent.putExtra("bolsaDestino", bolsaDestino)
            startActivity(intent)
        }
    }

    fun setListaExplorar(filtro: String): ArrayList<String>{
        var nombresDestinos = ArrayList<String>()
        if(filtro.equals("Todos")){
            nombresDestinos = Data.DESTINOS_LIST.map { it.nombre } as ArrayList<String>
        }else{
            nombresDestinos = Data.DESTINOS_LIST.filter { it.categoria == filtro }.map { it.nombre } as ArrayList<String>
        }
        return nombresDestinos
    }

    fun setListaFavoritos(): ArrayList<String>{
        var nombresDestinos = ArrayList<String>()
        nombresDestinos = Data.FAVORITOS_LIST.map { it.nombre } as ArrayList<String>
        return nombresDestinos
    }

    fun setRecomendacion(): Destino? {
        val categoriaMasRepetida = Data.FAVORITOS_LIST
            .groupingBy { it.categoria }
            .eachCount()
            .maxByOrNull { it.value }?.key ?: ""

        val destinosMasFavoritos = Data.FAVORITOS_LIST
            .filter { it.categoria == categoriaMasRepetida }
            .map { it }

        return if (destinosMasFavoritos.isNotEmpty()) {
            destinosMasFavoritos.random()
        } else {
            null
        }
    }
}