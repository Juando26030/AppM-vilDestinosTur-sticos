package com.example.taller1destinos.logica

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taller1destinos.R

class PantallaExplorar : AppCompatActivity() {
    private val TAG = "PantallaExplorar" // Define a clear TAG for logging

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_explorar)

        Log.d(TAG, "Entró a la pantalla Explorar") // Log entry for entering the activity

        // Variables
        val txtCategoria = findViewById<TextView>(R.id.textoCategoria)
        val bolsaRecibida = intent.getBundleExtra("bolsaCategoria")

        if (bolsaRecibida != null) {
            val categoria = bolsaRecibida.getString("categoria")
            Log.i(TAG, "Valor en la bolsa (categoria): $categoria") // Log entry with category value (if available)
            txtCategoria.text = categoria
        } else {
            Log.w(TAG, "No se encontró la bolsa 'bolsaCategoria' en el intent") // Log entry if the bundle is missing
            txtCategoria.text = "Categoría no disponible"
        }
    }
}