package co.edu.uniandes.miswmobile.vinilosapp.repositories

import android.app.Application
import co.edu.uniandes.miswmobile.vinilosapp.models.Performer
import co.edu.uniandes.miswmobile.vinilosapp.network.NetworkServiceAdapter
import com.android.volley.VolleyError

class PerformerRepository (val application: Application) {

    fun refreshData(callback: (List<Performer>)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getPerformer({
            callback(it)
        }, onError )
    }
}