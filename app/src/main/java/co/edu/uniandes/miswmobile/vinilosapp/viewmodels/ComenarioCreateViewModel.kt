package co.edu.uniandes.miswmobile.vinilosapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.edu.uniandes.miswmobile.vinilosapp.models.Album
import co.edu.uniandes.miswmobile.vinilosapp.models.Comentario
import co.edu.uniandes.miswmobile.vinilosapp.repositories.AlbumRepository
import co.edu.uniandes.miswmobile.vinilosapp.repositories.ComentarioRepository
import co.edu.uniandes.miswmobile.vinilosapp.repositories.TrackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ComenarioCreateViewModel(application: Application): AndroidViewModel(application) {

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown
    private val comentarioRepository = ComentarioRepository(application)

    suspend fun addComentarioToAlbum(comentario: Comentario, albumId: Int){
        try {
            viewModelScope.launch(Dispatchers.Default){
                withContext(Dispatchers.IO) {
                    comentarioRepository.addComentarioToAlbum(comentario, albumId)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ComenarioCreateViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ComenarioCreateViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}