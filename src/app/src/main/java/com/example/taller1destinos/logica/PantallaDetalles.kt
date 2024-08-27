package com.example.taller1destinos.logica

import android.os.Bundle
import android.util.Log
import android.view.View
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

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
        // Variables
        val titulo = findViewById<TextView>(R.id.txtTitulo)
        val btnAñadir = findViewById<Button>(R.id.btnAñadir)
        // Lógica para manejar bolsaRecibida
        val bolsaRecibida = intent.getBundleExtra("bolsaDestino")
        if (bolsaRecibida != null) {
            manejarBolsaRecibida(bolsaRecibida, btnAñadir, titulo)
        }
        // Lógica para manejar bolsaRecomendacion
        val bolsaRecomendacion = intent.getBundleExtra("bolsaRecomendacion")
        if (bolsaRecomendacion != null) {
            manejarBolsaRecomendacion(titulo, btnAñadir)
        }
    }

    private fun manejarBolsaRecibida(bolsaRecibida: Bundle, btnAñadir: Button, titulo: TextView) {
        val idRecibido = bolsaRecibida.getInt("id")
        val destino = buscarDestinoPorId(idRecibido, bolsaRecibida.getInt("tipo"))

        //Obtener objeto de destinos traducidos a Ingles para hacer la peticion a la API de clima
        val destinosTraducidosObj = JSONObject(Funciones.loadJSONFromAsset(this,"destinos-traducidos.json"))
        val destinosTraducidos = destinosTraducidosObj.getJSONObject("traduccion")

        destino?.let {

            //Obtener pais traducido
            var paisTraducido = destinosTraducidos.getString(destino.pais)

            //Definir URL de la peticion para realizar query con el pais traducido
            val urlApi = "http://api.weatherapi.com/v1/current.json?key=205847083bcd44bea3f205020242608&q=" + paisTraducido

            //Usar coroutine para realizar peticion en un hilo separado del principal
            CoroutineScope(Dispatchers.IO).launch {
                //Obtener clima
                val weather = Funciones.consultarClima(this, urlApi)[0]
                //Obtener temperatura
                val temperature = Funciones.consultarClima(this, urlApi)[1]
                if (weather != null) {
                    Log.d("api", weather)
                }else{
                    Log.i("api","Error obteniendo el clima")
                }

                //Actualizar textos del clima una vez finalizada la peticion
                withContext(Dispatchers.Main) {
                    val clima = findViewById<TextView>(R.id.txtClima)
                    val temperatura = findViewById<TextView>(R.id.txtTemperatura)
                    clima.text = "Clima: " + weather
                    temperatura.text = "Temperatura: " + temperature
                }
            }

            mostrarDetallesDestino(it)
            configurarBotonFavoritos(it, btnAñadir)
            titulo.text = it.nombre // Usar el nombre del destino como título
        } ?: run {
            mostrarErrorYSalir(idRecibido)
        }
    }

    private fun manejarBolsaRecomendacion(titulo: TextView, btnAñadir: Button) {
        val categoriaFavorita = encontrarCategoriaFavorita(Data.FAVORITOS_LIST)

        //Obtener objeto de destinos traducidos a Ingles para hacer la peticion a la API de clima
        val destinosTraducidosObj = JSONObject(Funciones.loadJSONFromAsset(this,"destinos-traducidos.json"))
        val destinosTraducidos = destinosTraducidosObj.getJSONObject("traduccion")

        if (categoriaFavorita == null) {
            titulo.text = "NA"
            Toast.makeText(this, "No tienes destinos favoritos para recomendar", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            val destinoRecomendado = seleccionarDestinoPorCategoriaEnFavoritos(categoriaFavorita)

            destinoRecomendado?.let {
                //Obtener pais traducido
                var paisTraducido = destinosTraducidos.getString(destinoRecomendado.pais)

                //Definir URL de la peticion para realizar query con el pais traducido
                val urlApi = "http://api.weatherapi.com/v1/current.json?key=205847083bcd44bea3f205020242608&q=" + paisTraducido

                //Usar coroutine para realizar peticion en un hilo separado del principal
                CoroutineScope(Dispatchers.IO).launch {
                    //Obtener clima
                    val weather = Funciones.consultarClima(this, urlApi)[0]
                    //Obtener temperatura
                    val temperature = Funciones.consultarClima(this, urlApi)[1]
                    if (weather != null) {
                        Log.d("api", weather)
                    }else{
                        Log.i("api","Error obteniendo el clima")
                    }

                    //Actualizar textos del clima
                    withContext(Dispatchers.Main) {
                        val clima = findViewById<TextView>(R.id.txtClima)
                        val temperatura = findViewById<TextView>(R.id.txtTemperatura)
                        clima.text = "Clima: " + weather
                        temperatura.text = "Temperatura: " + temperature
                    }
                }

                mostrarDetallesDestino(it)
                btnAñadir.visibility = View.GONE // Ocultar botón en recomendaciones
                titulo.text = "Recomendando por $categoriaFavorita"
            } ?: run {
                Toast.makeText(this, "No se encontró un destino recomendado", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun buscarDestinoPorId(id: Int, tipo: Int): Destino? {
        return if (tipo == 1) {
            Data.DESTINOS_LIST.find { it.id == id }
        } else {
            Data.FAVORITOS_LIST.find { it.id == id }
        }
    }

    private fun mostrarDetallesDestino(destino: Destino) {
        findViewById<TextView>(R.id.txtNombre).text = destino.nombre
        findViewById<TextView>(R.id.txtPais).text = destino.pais
        findViewById<TextView>(R.id.txtCategoria).text = destino.categoria
        findViewById<TextView>(R.id.txtPlan).text = destino.plan
        findViewById<TextView>(R.id.txtPrecio).text = "USD ${destino.precio}"
    }

    private fun configurarBotonFavoritos(destino: Destino, btnAñadir: Button) {
        btnAñadir.text = if (Data.FAVORITOS_LIST.contains(destino)) {
            "Eliminar de favoritos"
        } else {
            "Añadir a favoritos"
        }

        btnAñadir.setOnClickListener {
            toggleFavorito(destino, btnAñadir)
        }
    }

    private fun toggleFavorito(destino: Destino, btnAñadir: Button) {
        if (Data.FAVORITOS_LIST.contains(destino)) {
            Data.FAVORITOS_LIST.remove(destino)
            Toast.makeText(this, "Eliminado de favoritos", Toast.LENGTH_SHORT).show()
            btnAñadir.text = "Añadir a favoritos"
        } else {
            Data.FAVORITOS_LIST.add(destino)
            Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
            btnAñadir.text = "Eliminar de favoritos"
        }
    }

    private fun encontrarCategoriaFavorita(favoritos: List<Destino>): String? {
        if (favoritos.isEmpty()) return null

        val categorias = favoritos.groupingBy { it.categoria }.eachCount()
        val maxRepeticiones = categorias.values.maxOrNull() ?: 0
        val categoriasFavoritas = categorias.filterValues { it == maxRepeticiones }.keys
        return categoriasFavoritas.random() // Elige una categoría al azar si hay empate
    }

    private fun seleccionarDestinoPorCategoriaEnFavoritos(categoria: String): Destino? {
        // Filtra los destinos en favoritos por la categoría más repetida y selecciona uno al azar
        return Data.FAVORITOS_LIST.filter { it.categoria == categoria }.randomOrNull()
    }

    private fun mostrarErrorYSalir(id: Int) {
        Log.e("PantallaDetalles", "Destino con id $id no encontrado")
        Toast.makeText(this, "Destino no encontrado", Toast.LENGTH_SHORT).show()
        finish() // Cierra la actividad si no se encuentra el destino
    }
}
