package co.edu.uniandes.miswmobile.vinilosapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.edu.uniandes.miswmobile.vinilosapp.models.Track
import co.edu.uniandes.miswmobile.vinilosapp.repositories.TrackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrackViewModel(application: Application, idAlbum: Int): AndroidViewModel(application) {

    private val trackRepository = TrackRepository(application)
    private val _tracks = MutableLiveData<List<Track>>()
    private val _idAlbum = idAlbum

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val tracks: LiveData<List<Track>>
        get() = _tracks
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch (Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    var data = trackRepository.getTracks(_idAlbum)
                    _tracks.postValue(data)
                    Log.d("track", "Tracks in albumId: " + _idAlbum + " - "  + data.size)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e: Exception) {
            Log.d("track", e.localizedMessage)
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, private val idAlbum: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TrackViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TrackViewModel(app, idAlbum) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}