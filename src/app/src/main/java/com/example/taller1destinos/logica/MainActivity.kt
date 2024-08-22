package com.example.taller1destinos.logica

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.taller1destinos.R
import org.json.JSONObject

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Variables
        val spinner = findViewById<Spinner>(R.id.spinner)
        val btnExplorar = findViewById<Button>(R.id.btnExplorar)
        val btnFavoritos = findViewById<Button>(R.id.btnFavoritos)
        val btnRecomendaciones = findViewById<Button>(R.id.btnRecomendaciones)

        Funciones.guardarDestinosJson(this)
        logicaBotones(spinner, btnExplorar, btnFavoritos, btnRecomendaciones)


    }

    fun logicaBotones(spinner: Spinner, btnExplorar: Button, btnFavoritos: Button, btnRecomendaciones: Button){

        btnExplorar.setOnClickListener {
            val selectedItem = spinner.selectedItem?.toString() ?: ""  // Get selected item (or empty string)
            enviarBolsa(selectedItem, 1, PantallaExplorar::class.java)
        }

        btnFavoritos.setOnClickListener {
            val selectedItem = spinner.selectedItem?.toString() ?: ""
            enviarBolsa(selectedItem, 2, PantallaExplorar::class.java)
            Log.e("Pantalla Activity", "Enviado a favoritos")
        }

        btnRecomendaciones.setOnClickListener {
            val selectedItem = spinner.selectedItem?.toString() ?: ""
            enviarBolsa(selectedItem, 3, PantallaRecomendaciones::class.java)
        }
    }

    private fun enviarBolsa(categoria: String, tipo: Int, targetClass: Class<out AppCompatActivity>) {
        val bolsaCategoria = Bundle()
        bolsaCategoria.putString("categoria", categoria)
        bolsaCategoria.putInt("tipo", tipo)
        val peticion = Intent(this, targetClass)
        peticion.putExtra("bolsaCategoria", bolsaCategoria)
        startActivity(peticion)
    }
}