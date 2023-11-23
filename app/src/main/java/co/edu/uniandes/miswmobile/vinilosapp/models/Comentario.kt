package co.edu.uniandes.miswmobile.vinilosapp.models

import org.json.JSONObject

data class Comentario(
    val id: Int? = null,
    val description: String,
    val rating: String,
    val collector: JSONObject
)