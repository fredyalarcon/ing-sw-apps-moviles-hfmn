package co.edu.uniandes.miswmobile.vinilosapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.edu.uniandes.miswmobile.vinilosapp.models.Comentario
import co.edu.uniandes.miswmobile.vinilosapp.repositories.ComentarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ComentarioViewModel(application: Application, idAlbum: Int): AndroidViewModel(application) {

    private val comentarioRepository = ComentarioRepository(application)
    private val _comentarios = MutableLiveData<List<Comentario>>()
    private val _idAlbum = idAlbum

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    private lateinit var _current_comentarios: List<Comentario>

    private val _comentarioToAdd = MutableLiveData<HashMap<Int?, String>>()

    val comentarios: LiveData<List<Comentario>>
        get() = _comentarios
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    /*fun getComentario(id: Int) {
        try{
            viewModelScope.launch (Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    var data = comentarioRepository.getComentarios(id)
                    _comentarios.postValue(data)

                    var toAdd = _current_comentarios.associateBy({it.id}, {it.description})

                    if (data.isNotEmpty()) {
                        val keys = data.map { x -> x.id }
                        toAdd = toAdd.filterKeys { key -> !keys.contains(key) }
                    }

                    _comentarioToAdd.postValue(toAdd as HashMap<Int?, String>?)
                    _eventNetworkError.postValue(false)
                    _isNetworkErrorShown.postValue(false)
                }
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
    }

    fun addComentarioToAlbum(comentario: Comentario, albumId: Int){
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
    }*/

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch (Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    var data = comentarioRepository.getComentarios(_idAlbum)
                    _comentarios.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e: Exception) {
            Log.d("comentario", e.localizedMessage)
            _eventNetworkError.value = true
        }
    }
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, private val idAlbum: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ComentarioViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ComentarioViewModel(app, idAlbum) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}