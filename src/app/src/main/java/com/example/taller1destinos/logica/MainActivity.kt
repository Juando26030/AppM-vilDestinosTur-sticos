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

class MainActivity : AppCompatActivity() , AdapterView.OnItemSelectedListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Variables
        val spinner = findViewById<Spinner>(R.id.spinner)
        val btnExplorar = findViewById<Button>(R.id.btnExplorar)
        val btnFavoritos = findViewById<Button>(R.id.btnFavoritos)
        val btnRecomendaciones = findViewById<Button>(R.id.btnRecomendaciones)

        //Lógica
        spinner.onItemSelectedListener = this
        if(spinner.selectedItem != null){
            logicaBotones(spinner.toString(), btnExplorar, btnFavoritos, btnRecomendaciones)
        }
    }

    fun logicaBotones(categoria: String, btnExplorar: Button, btnFavoritos: Button, btnRecomendaciones: Button){

        val bolsaCategoria = Bundle()
        bolsaCategoria.putString("categoria", categoria)

        //Pantalla explorar
        btnExplorar.setOnClickListener{
            val peticion = Intent(this, PantallaExplorar::class.java)
            peticion.putExtra("bolsaCategoria", bolsaCategoria)
            startActivity(peticion)
        }
        //Pantalla favoritos
        btnFavoritos.setOnClickListener{
            val peticion = Intent(this, PantallaFavoritos::class.java)
            peticion.putExtra("bolsaCategoria", bolsaCategoria)
            startActivity(peticion)
        }
        //Pantalla recomendaciones
        btnRecomendaciones.setOnClickListener{
            val peticion = Intent(this, PantallaRecomendaciones::class.java)
            peticion.putExtra("bolsaCategoria", bolsaCategoria)
            startActivity(peticion)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}