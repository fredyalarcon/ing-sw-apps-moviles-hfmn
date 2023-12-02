package co.edu.uniandes.miswmobile.vinilosapp.repositories

import android.app.Application
import co.edu.uniandes.miswmobile.vinilosapp.models.Track
import co.edu.uniandes.miswmobile.vinilosapp.network.NetworkServiceAdapter
import org.json.JSONObject

class TrackRepository(val application: Application) {

    suspend fun getTracks(idAlbum: Int): List<Track> {
        return NetworkServiceAdapter.getInstance(application).getTracks(idAlbum)
    }

    suspend fun addTrack(track: Track, idAlbum: Int): JSONObject {
        return NetworkServiceAdapter.getInstance(application).addTrack(track, idAlbum)
    }
}