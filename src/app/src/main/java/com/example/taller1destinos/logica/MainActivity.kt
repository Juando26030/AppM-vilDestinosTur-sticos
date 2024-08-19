package com.example.taller1destinos.logica

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taller1destinos.R

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Variables
        val spinner = findViewById<Spinner>(R.id.spinner)
        val btnExplorar = findViewById<Button>(R.id.btnExplorar)
        val btnFavoritos = findViewById<Button>(R.id.btnFavoritos)
        val btnRecomendaciones = findViewById<Button>(R.id.btnRecomendaciones)

        spinner.onItemSelectedListener = this

        // Button click listeners (logic moved here)
        btnExplorar.setOnClickListener {
            val selectedItem = spinner.selectedItem?.toString() ?: ""  // Get selected item (or empty string)
            enviarCategoria(selectedItem, PantallaExplorar::class.java)
        }

        btnFavoritos.setOnClickListener {
            val selectedItem = spinner.selectedItem?.toString() ?: ""
            enviarCategoria(selectedItem, PantallaFavoritos::class.java)
        }

        btnRecomendaciones.setOnClickListener {
            val selectedItem = spinner.selectedItem?.toString() ?: ""
            enviarCategoria(selectedItem, PantallaRecomendaciones::class.java)
        }
    }

    private fun enviarCategoria(categoria: String, targetClass: Class<out AppCompatActivity>) {
        val bolsaCategoria = Bundle()
        bolsaCategoria.putString("categoria", categoria)

        val peticion = Intent(this, targetClass)
        peticion.putExtra("bolsaCategoria", bolsaCategoria)
        startActivity(peticion)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // Implement your logic here when an item is selected
        // For example, you might update a UI element based on the selection
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Implement your logic here when nothing is selected
    }
}