package co.edu.uniandes.miswmobile.vinilosapp.repositories

import android.app.Application
import co.edu.uniandes.miswmobile.vinilosapp.models.Comentario
import co.edu.uniandes.miswmobile.vinilosapp.models.Track
import co.edu.uniandes.miswmobile.vinilosapp.network.NetworkServiceAdapter
import org.json.JSONObject

class ComentarioRepository(val application: Application) {
    suspend fun getComentarios(idAlbum: Int): List<Comentario> {
        return NetworkServiceAdapter.getInstance(application).getComentario(idAlbum)
    }
}