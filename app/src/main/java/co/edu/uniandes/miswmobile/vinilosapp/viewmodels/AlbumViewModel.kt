package co.edu.uniandes.miswmobile.vinilosapp.viewmodels


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.test.espresso.IdlingResource
import co.edu.uniandes.miswmobile.vinilosapp.models.Album
import co.edu.uniandes.miswmobile.vinilosapp.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class AlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val _albums = MutableLiveData<List<Album>>()
    private val _performerAlbums = MutableLiveData<List<Album>>()
    private val _performerAlbumsToAdd = MutableLiveData<HashMap<Int?, String>>()
    private val _album = MutableLiveData<Album>()
    private lateinit var _current_albums: List<Album>

    val albums: LiveData<List<Album>>
        get() = Transformations.map(_albums) { albumsList ->
            // álbumes ordenados por nombre
            albumsList.sortedBy { it.name }.map { album ->
                album.copy(releaseDate = formatearFecha(album.releaseDate))
            }
        }

    val performerAlbums: LiveData<List<Album>>
        get() = Transformations.map(_performerAlbums) { albumsList ->
            albumsList.sortedBy { it.name }.map { album ->
                album.copy(releaseDate = formatearFecha(album.releaseDate))
            }
        }

    val performerAlbumsToAdd: LiveData<HashMap<Int?, String>>
        get() = _performerAlbumsToAdd

    val album: LiveData<Album>
        get() = _album

    // Función para formatear la fecha
    private fun formatearFecha(fechaString: String?): String {
        if (fechaString.isNullOrEmpty()) {
            return ""
        }

        try {
            val formatoOriginal = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val fecha = formatoOriginal.parse(fechaString)

            val formatoDeseado = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            return formatoDeseado.format(fecha)
        } catch (e: ParseException) {
            // Manejar la excepción o imprimir un mensaje de registro
            e.printStackTrace()
            return ""
        }
    }

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown


    private val albumsRepository = AlbumRepository(application)

    fun refreshDataFromNetwork() {
        try{

            viewModelScope.launch (Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    var data = albumsRepository.refreshData()
                    _albums.postValue(data)
                    _current_albums = data
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
    }

    fun getPerformerAlbums(type: String, id: Int) {
        try{
            viewModelScope.launch (Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    var data = albumsRepository.getPerformerAlbums(type, id)
                    _performerAlbums.postValue(data)

                    var toAdd = _current_albums.associateBy({it.albumId}, {it.name})

                    if (data.isNotEmpty()) {
                        val keys = data.map { x -> x.albumId }
                        toAdd = toAdd.filterKeys { key -> !keys.contains(key) }
                    }

                    _performerAlbumsToAdd.postValue(toAdd as HashMap<Int?, String>?)
                    _eventNetworkError.postValue(false)
                    _isNetworkErrorShown.postValue(false)
                }
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
    }

    fun addAlbumToPerformer(type: String, performerId: Int, albumId: Int){
        try {
            viewModelScope.launch(Dispatchers.Default){
                withContext(Dispatchers.IO) {
                    albumsRepository.addAlbumToPerformer(type, performerId, albumId)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
    }


    fun getAlbum(id: Int){
        try {
            viewModelScope.launch(Dispatchers.Default){
                withContext(Dispatchers.IO) {
                    var data = albumsRepository.detailAlbum(id)
                    _album.postValue(data)
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
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}