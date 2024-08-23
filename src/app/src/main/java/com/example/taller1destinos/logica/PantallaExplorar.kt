package com.example.taller1destinos.logica

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.taller1destinos.R
import com.example.taller1destinos.datos.Data
import com.example.taller1destinos.datos.Destino

class PantallaExplorar : AppCompatActivity() {
    private val TAG = "PantallaExplorar"
    private lateinit var txtCategoria: TextView
    private lateinit var lista: ListView
    private var tipo: Int = 1
    private var filtroCategoria: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_explorar)

        Log.d(TAG, "Entró a la pantalla Explorar")

        // Variables
        txtCategoria = findViewById(R.id.txtCategoria)
        lista = findViewById(R.id.listDestinos)
        val bolsaRecibida = intent.getBundleExtra("bolsaCategoria")

        if (bolsaRecibida != null) {
            filtroCategoria = bolsaRecibida.getString("categoria") ?: ""
            tipo = bolsaRecibida.getInt("tipo")
            Log.i(TAG, "Valor en la bolsa (categoria): $filtroCategoria")

            if (tipo == 1) {
                txtCategoria.text = "Filtrando por: $filtroCategoria"
            } else if (tipo == 2) {
                txtCategoria.text = "Destinos favoritos"
            }

            definirPantallas(filtroCategoria, tipo)
        } else {
            Log.w(TAG, "No se encontró la bolsa 'bolsaCategoria' en el intent")
            txtCategoria.text = "Categoría no disponible"
        }
    }

    override fun onResume() {
        super.onResume()
        // Actualiza la lista cada vez que se vuelve a esta pantalla
        definirPantallas(filtroCategoria, tipo)
    }

    fun definirPantallas(filtro: String, tipo: Int) {
        val destinos: List<Destino> = if (tipo == 1) {
            setListaExplorar(filtro)
        } else {
            setListaFavoritos()
        }

        val nombresDestinos = destinos.map { it.nombre }
        Log.e(TAG, "Nombres de destinos: $nombresDestinos")

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            nombresDestinos
        )
        lista.adapter = adaptador

        lista.setOnItemClickListener { _, _, position, _ ->
            val destinoSeleccionado = destinos[position]
            val intent = Intent(this, PantallaDetalles::class.java)
            val bolsaDestino = Bundle().apply {
                putInt("id", destinoSeleccionado.id)
                putInt("tipo", tipo)
            }
            intent.putExtra("bolsaDestino", bolsaDestino)
            startActivity(intent)
        }
    }

    fun setListaExplorar(filtro: String): List<Destino> {
        return if (filtro.equals("Todos", ignoreCase = true)) {
            Data.DESTINOS_LIST
        } else {
            Data.DESTINOS_LIST.filter { it.categoria == filtro }
        }
    }

    fun setListaFavoritos(): List<Destino> {
        return Data.FAVORITOS_LIST
    }
}
