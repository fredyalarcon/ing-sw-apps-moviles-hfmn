package co.edu.uniandes.miswmobile.vinilosapp.network

import android.content.Context
import co.edu.uniandes.miswmobile.vinilosapp.models.Album
import com.android.volley.VolleyError

class MockNetworkServiceAdapter constructor(context: Context): NetworkServiceAdapter(context) {

    companion object{
        var instance: MockNetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: MockNetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    override fun getAlbums(onComplete: (resp: List<Album>) -> Unit, onError: (error: VolleyError) -> Unit) {
        val list = mutableListOf<Album>()
//        list.add(Album(
//            albumId = 1,
//            name = "Legend",
//            cover = "https://en.wikipedia.org/wiki/File:BobMarley-Legend.jpg",
//            releaseDate = "2012-04-23T18:25:43.511Z",
//            description = "Legend is a compilation album by Bob Marley and the Wailers. It was released in May 1984 by Island Records.",
//            genre = "Rock",
//            recordLabel = "Elektra"
//        ))
        onComplete(list)
    }

}