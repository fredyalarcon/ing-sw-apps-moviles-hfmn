package co.edu.uniandes.miswmobile.vinilosapp.models

data class Album (
    val albumId:Int? = null,
    val name:String,
    val cover:String,
    val releaseDate:String,
    val description:String,
    val genre:String,
    val recordLabel:String
)