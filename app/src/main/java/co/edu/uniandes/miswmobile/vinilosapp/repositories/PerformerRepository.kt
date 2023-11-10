package co.edu.uniandes.miswmobile.vinilosapp.repositories

import android.app.Application
import co.edu.uniandes.miswmobile.vinilosapp.models.Performer
import co.edu.uniandes.miswmobile.vinilosapp.network.NetworkServiceAdapter

class PerformerRepository (val application: Application) {

    suspend fun refreshData(): List<Performer> {
        return NetworkServiceAdapter.getInstance(application).getPerformer()
    }
}