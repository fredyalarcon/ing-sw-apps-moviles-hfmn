package co.edu.uniandes.miswmobile.vinilosapp.repositories

import android.app.Application
import co.edu.uniandes.miswmobile.vinilosapp.models.Album
import co.edu.uniandes.miswmobile.vinilosapp.models.Performer
import co.edu.uniandes.miswmobile.vinilosapp.network.NetworkServiceAdapter
import com.android.volley.VolleyError

class PerformerRepository (val application: Application) {

    suspend fun refreshData(): List<Performer>  {
        return NetworkServiceAdapter.getInstance(application).getPerformer()
    }
}