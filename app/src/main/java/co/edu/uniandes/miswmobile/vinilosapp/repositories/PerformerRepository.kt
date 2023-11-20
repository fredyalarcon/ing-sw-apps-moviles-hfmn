package co.edu.uniandes.miswmobile.vinilosapp.repositories

import android.app.Application
import co.edu.uniandes.miswmobile.vinilosapp.models.Album
import co.edu.uniandes.miswmobile.vinilosapp.models.Band
import co.edu.uniandes.miswmobile.vinilosapp.models.Musician
import co.edu.uniandes.miswmobile.vinilosapp.models.Performer
import co.edu.uniandes.miswmobile.vinilosapp.network.NetworkServiceAdapter

class PerformerRepository (val application: Application) {

    suspend fun refreshData(): List<Performer> {
        return NetworkServiceAdapter.getInstance(application).getPerformer()
    }

    suspend fun getMusician(id: Int): Musician {
        return NetworkServiceAdapter.getInstance(application).getMusician(id)
    }

    suspend fun getBand(id: Int): Band {
        return NetworkServiceAdapter.getInstance(application).getBand(id)
    }
}