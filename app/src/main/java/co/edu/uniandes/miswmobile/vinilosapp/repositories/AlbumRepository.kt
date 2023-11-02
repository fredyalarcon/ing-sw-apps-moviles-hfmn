package co.edu.uniandes.miswmobile.vinilosapp.repositories

import android.app.Application
import co.edu.uniandes.miswmobile.vinilosapp.models.Album
import co.edu.uniandes.miswmobile.vinilosapp.network.MockNetworkServiceAdapter
import co.edu.uniandes.miswmobile.vinilosapp.network.NetworkServiceAdapter
import com.android.volley.VolleyError

class AlbumRepository (val application: Application){
    fun refreshData(callback: (List<Album>)->Unit, onError: (VolleyError)->Unit) {
        val isRunningTest : Boolean by lazy {
            try {
                Class.forName("androidx.test.espresso.Espresso")
                true
            } catch (e: ClassNotFoundException) {
                false
            }
        }

        if (isRunningTest) {
            MockNetworkServiceAdapter.getInstance(application).getAlbums({
                callback(it)
            }, onError)
        } else {
            NetworkServiceAdapter.getInstance(application).getAlbums({
                callback(it)
            }, onError)
        }
    }
}