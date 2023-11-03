package co.edu.uniandes.miswmobile.vinilosapp.repositories

import android.app.Application
import co.edu.uniandes.miswmobile.vinilosapp.models.Musician
import co.edu.uniandes.miswmobile.vinilosapp.network.NetworkServiceAdapter
import com.android.volley.VolleyError

class MusiciansRepository (val application: Application) {

    fun refreshData(callback: (List<Musician>)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getMusician({
            callback(it)
        }, onError )
    }
}