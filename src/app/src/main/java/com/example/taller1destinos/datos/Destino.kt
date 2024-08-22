// En el archivo Destino.kt dentro del package datos
package com.example.taller1destinos.datos

data class Destino(
    val id: Int,
    val nombre: String,
    val pais: String,
    val categoria: String,
    val plan: String,
    val precio: Int
)
