package co.edu.uniandes.miswmobile.vinilosapp.repositories

import android.app.Application
import co.edu.uniandes.miswmobile.vinilosapp.models.Comentario
import co.edu.uniandes.miswmobile.vinilosapp.network.NetworkServiceAdapter
import org.json.JSONObject

class ComentarioRepository(val application: Application) {


    suspend fun getComentarios(id: Int): List<Comentario> {
        return NetworkServiceAdapter.getInstance(application).getComentario(id)
    }

    suspend fun addComentarioToAlbum(comentario: Comentario, albumId: Int): JSONObject {
        return NetworkServiceAdapter.getInstance(application).addComentarioToAlbum(comentario, albumId)
    }
}