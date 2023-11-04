package co.edu.uniandes.miswmobile.vinilosapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.edu.uniandes.miswmobile.vinilosapp.models.Performer
import co.edu.uniandes.miswmobile.vinilosapp.repositories.PerformerRepository

class ArtistViewModel(application: Application) :  AndroidViewModel(application) {

    private val performerRepository = PerformerRepository(application)
    private val _artists = MutableLiveData<List<Performer>>()
    val artists: LiveData<List<Performer>>
//        get() = _artists
        get() = Transformations.map(_artists) { performersList ->
            // performers ordenados por nombre
            performersList.sortedBy { it.name }.map { performer ->
                performer
            }
        }
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init{
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        performerRepository.refreshData({
            _artists.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        }, {
            _eventNetworkError.value = true
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}