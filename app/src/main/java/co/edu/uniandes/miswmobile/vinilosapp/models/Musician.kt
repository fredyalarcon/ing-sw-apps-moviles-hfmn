package co.edu.uniandes.miswmobile.vinilosapp.models

data class Musician (
    var birthDate: String,
    override val performerId:Int,
    override val name: String,
    override val image: String,
    override val description: String
) : Performer(performerId, name, image, description)