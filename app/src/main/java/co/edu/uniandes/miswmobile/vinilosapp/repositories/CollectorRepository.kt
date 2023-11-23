package co.edu.uniandes.miswmobile.vinilosapp.repositories

import android.app.Application
import co.edu.uniandes.miswmobile.vinilosapp.models.Collector
import co.edu.uniandes.miswmobile.vinilosapp.network.NetworkServiceAdapter

class CollectorRepository(val application: Application) {

    suspend fun refreshData(): List<Collector> {
        return NetworkServiceAdapter.getInstance(application).getCollectors()
    }
}