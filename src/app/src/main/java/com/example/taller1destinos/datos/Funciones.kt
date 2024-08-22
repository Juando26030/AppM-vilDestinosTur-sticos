import android.content.Context
import android.util.Log
import com.example.taller1destinos.datos.Data
import com.example.taller1destinos.datos.Destino
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class Funciones {
    companion object {
        fun guardarDestinosJson(context: Context) {
            val jsonString = loadJSONFromAsset(context)

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

        fun loadJSONFromAsset(context: Context): String? {
            return try {
                val isStream: InputStream = context.assets.open("destinos.json")
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
    }
}
