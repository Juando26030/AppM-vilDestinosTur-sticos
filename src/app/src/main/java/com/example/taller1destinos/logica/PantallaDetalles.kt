package com.example.taller1destinos.logica

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taller1destinos.R
import com.example.taller1destinos.datos.Data
import com.example.taller1destinos.datos.Destino

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
        val nombre = findViewById<TextView>(R.id.txtNombre)
        val pais = findViewById<TextView>(R.id.txtPais)
        val categoria = findViewById<TextView>(R.id.txtCategoria)
        val plan = findViewById<TextView>(R.id.txtPlan)
        val precio = findViewById<TextView>(R.id.txtPrecio)
        val btnAñadir = findViewById<Button>(R.id.btnAñadir)
        val bolsaRecibida = intent.getBundleExtra("bolsaDestino")
        val idRecibido: Int

        val destino: Destino
        if (bolsaRecibida != null) {
            idRecibido = (bolsaRecibida.getInt("id")?: "") as Int// Log entry with category value (if available)
            if(bolsaRecibida.getInt("tipo") == 1){
                destino = Data.DESTINOS_LIST[idRecibido]
            }else{
                destino = Data.FAVORITOS_LIST[idRecibido]
            }
            nombre.text = destino.nombre
            pais.text = destino.pais
            categoria.text = destino.categoria
            plan.text = destino.plan
            precio.text = "USD " + destino.precio.toString()
            logicaBoton(btnAñadir, destino)
        }
    }

    fun logicaBoton(btnAñadir: Button, destino: Destino) {
        btnAñadir.setOnClickListener {
            val esFavorito = Data.FAVORITOS_LIST.contains(destino)
            if (!esFavorito) {
                // El destino no está en favoritos, lo agregamos
                Data.FAVORITOS_LIST.add(destino)
                Toast.makeText(baseContext, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
                btnAñadir.text = "Eliminar de favoritos"
            } else {
                // El destino ya está en favoritos, lo eliminamos
                Data.FAVORITOS_LIST.remove(destino)
                Toast.makeText(baseContext, "Eliminado de favoritos", Toast.LENGTH_SHORT).show()
                btnAñadir.text = "Anadir a favoritos"
            }

        }
    }
}