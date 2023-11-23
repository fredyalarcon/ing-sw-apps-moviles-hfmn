package co.edu.uniandes.miswmobile.vinilosapp.network

import android.content.Context
import co.edu.uniandes.miswmobile.vinilosapp.models.Album
import co.edu.uniandes.miswmobile.vinilosapp.models.Band
import co.edu.uniandes.miswmobile.vinilosapp.models.Musician
import co.edu.uniandes.miswmobile.vinilosapp.models.Performer
import com.android.volley.Response
import com.android.volley.VolleyError
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MockNetworkServiceAdapter constructor(context: Context) : NetworkServiceAdapter(context) {

    companion object {
        var instance: MockNetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: MockNetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    override suspend fun getAlbum(id: Int) = suspendCoroutine<Album> { cont ->
        cont.resume(
            Album(
                albumId = 1,
                name = "Legend",
                cover = "https://en.wikipedia.org/wiki/File:BobMarley-Legend.jpg",
                releaseDate = "2012-04-23T18:25:43.511Z",
                description = "Legend is a compilation album by Bob Marley and the Wailers. It was released in May 1984 by Island Records.",
                genre = "Rock",
                recordLabel = "Elektra"
            )
        )

    }

    override suspend fun getPerformer() = suspendCoroutine<List<Performer>> { cont ->
        val list = mutableListOf<Performer>()
        list.add(
            Musician(
                birthDate = "1948-07-16T00:00:00.000Z",
                performerId = 100,
                name = "Rubén Blades Bellido de Luna",
                image = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bb/Ruben_Blades_by_Gage_Skidmore.jpg/800px-Ruben_Blades_by_Gage_Skidmore.jpg",
                description = "Es un cantante, compositor, músico, actor, abogado, político y activista panameño. Ha desarrollado gran parte de su carrera artística en la ciudad de Nueva York."
            )
        )

        list.add(
            Band(
                performerId = 101,
                name = "Queen",
                image = "https://pm1.narvii.com/6724/a8b29909071e9d08517b40c748b6689649372852v2_hq.jpg",
                description = "Queen es una banda británica de rock formada en 1970 en Londres por el cantante Freddie Mercury, el guitarrista Brian May, el baterista Roger Taylor y el bajista John Deacon. Si bien el grupo ha presentado bajas de dos de sus miembros (Mercury, fallecido en 1991, y Deacon, retirado en 1997), los integrantes restantes, May y Taylor, continúan trabajando bajo el nombre Queen, por lo que la banda aún se considera activa.",
                creationDate = "1970-01-01T00:00:00.000Z"
            )
        )

        cont.resume(list)

    }

}