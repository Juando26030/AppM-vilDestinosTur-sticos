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
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taller1destinos.R
import com.example.taller1destinos.datos.Data
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class PantallaFavoritos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla_favoritos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        //Web view
        val web = findViewById<WebView>(R.id.webView)
        web.webViewClient = WebViewClient()
        web.loadUrl("https://gemini.google.com/app")


        //Guardar el json en un arreglo para la list
        /*val arreglo = ArrayList<String>()
        val json = JSONObject(loadJSONFromAsset())
        val paisesJson = json.getJSONArray("paises")
        for (i in 0 until paisesJson.length()){
            val jsonObject = paisesJson.getJSONObject(i)
            arreglo.add(jsonObject.getString("capital"))
        }*/

        //List view
        //ArrayAdapter -> contexto, forma visual, datos
        /*val lista = findViewById<ListView>(R.id.listView)
        val categorias = resources.getStringArray(R.array.categorias)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            categorias
        )*/

        val lista = findViewById<ListView>(R.id.listView)
        val nombresDestinos = Data.DESTINOS_LIST.map { it.nombre }
        Log.e("PantallaFavoritosTAG", "Nombres de destinos: $nombresDestinos")

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            nombresDestinos
        )
        lista.adapter = adaptador

        lista.setOnItemClickListener(object: AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(baseContext, position.toString(), Toast.LENGTH_SHORT).show()
            }
        })


    }

    //Leer json
    fun loadJSONFromAsset(): String? {
        var json: String? = null
        try {
            val isStream: InputStream = assets.open("paises.json")
            val size:Int = isStream.available()
            val buffer = ByteArray(size)
            isStream.read(buffer)
            isStream.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

}