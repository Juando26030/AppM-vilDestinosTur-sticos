package com.example.taller1destinos.logica

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taller1destinos.R

class PantallaDetalles : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla_detalles)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val titulo = findViewById<TextView>(R.id.textView)
        val bolsaRecibida = intent.getBundleExtra("bolsaDestino")
        val idRecibido: Int
        if (bolsaRecibida != null) {
            idRecibido = (bolsaRecibida.getInt("id")?: "") as Int// Log entry with category value (if available)
            titulo.text = "Filtrando por: " + idRecibido

        titulo.text
        }
    }
}