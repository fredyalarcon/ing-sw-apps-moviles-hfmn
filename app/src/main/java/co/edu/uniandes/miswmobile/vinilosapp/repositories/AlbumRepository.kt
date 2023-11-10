package co.edu.uniandes.miswmobile.vinilosapp.repositories

import android.app.Application
import co.edu.uniandes.miswmobile.vinilosapp.models.Album
import co.edu.uniandes.miswmobile.vinilosapp.network.MockNetworkServiceAdapter
import co.edu.uniandes.miswmobile.vinilosapp.network.NetworkServiceAdapter

class AlbumRepository (val application: Application){
    suspend fun refreshData(): List<Album> {

        val isRunningTest : Boolean by lazy {
            try {
                Class.forName("androidx.test.espresso.Espresso")
                true
            } catch (e: ClassNotFoundException) {
                false
            }
        }

        return if (isRunningTest) {
            MockNetworkServiceAdapter.getInstance(application).getAlbums()
        } else {
            NetworkServiceAdapter.getInstance(application).getAlbums()
        }
    }
}