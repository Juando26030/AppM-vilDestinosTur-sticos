import android.content.Context
import android.util.Log
import com.example.taller1destinos.datos.Data
import com.example.taller1destinos.datos.Destino
import kotlinx.coroutines.CoroutineScope
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL

class Funciones {
    companion object {
        fun guardarDestinosJson(context: Context) {
            val jsonString = loadJSONFromAsset(context,"destinos.json")

            if (jsonString != null) {
                try {
                    val json = JSONObject(jsonString)
                    val destinosJson = json.getJSONArray("destinos")

                    // Limpia la lista antes de llenarla, por si ya tiene datos
                    Data.DESTINOS_LIST.clear()

                    for (i in 0 until destinosJson.length()) {
                        val jsonObject = destinosJson.getJSONObject(i)
                        val destino = Destino(
                            id = jsonObject.getInt("id"),
                            nombre = jsonObject.getString("nombre"),
                            pais = jsonObject.getString("pais"),
                            categoria = jsonObject.getString("categoria"),
                            plan = jsonObject.getString("plan"),
                            precio = jsonObject.getInt("precio")
                        )
                        Data.DESTINOS_LIST.add(destino)
                    }
                    Log.d("Funciones", "Destinos cargados exitosamente: ${Data.DESTINOS_LIST.size}")
                } catch (e: Exception) {
                    Log.e("Funciones", "Error procesando el JSON: ${e.message}")
                }
            } else {
                // Manejar el caso cuando el JSON no se puede cargar
                Log.e("Funciones", "No se pudo cargar el archivo destinos.json")
            }
        }

        fun loadJSONFromAsset(context: Context,filename: String): String? {
            return try {
                val isStream: InputStream = context.assets.open(filename)
                val size: Int = isStream.available()
                val buffer = ByteArray(size)
                isStream.read(buffer)
                isStream.close()
                String(buffer, Charsets.UTF_8)
            } catch (ex: IOException) {
                Log.e("Funciones", "Error leyendo el archivo destinos.json: ${ex.message}")
                null
            }
        }

        fun consultarClima(context: CoroutineScope, url: String) : Array<String>{
            try {
                //Crear objeto para la url
                val url : URL = URI.create(url).toURL()
                //Establecer conexion con la API
                val connection : HttpURLConnection = url.openConnection() as HttpURLConnection

                //Metodo de peticion: GET
                connection.requestMethod = "GET"

                // Codigo de respuesta
                val responseCode: Int = connection.responseCode
                Log.d("api","Response Code: $responseCode")

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Leer e imprimir el response
                    val reader : BufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                    var line: String?
                    val response = StringBuilder()

                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }

                    reader.close()

                    Log.i("api","Response Data: $response")
                    //Extraer informacion del clima
                    val weather = JSONObject(response.toString()).getJSONObject("current").getJSONObject("condition").getString("text")
                    //Extraer informacion de la temperatura
                    val temperature = JSONObject(response.toString()).getJSONObject("current").getString("temp_c")

                    return arrayOf(weather,temperature.toString())
                } else {
                    Log.i("api","Error: No fue posible obtener la informacion de la API")
                    return arrayOf("","")
                }
                // Cerrar la conexion
                connection.disconnect()
            } catch (e: Exception) {
                Log.i("api", e.toString())
                return arrayOf("","")
            }
        }

    }
}
