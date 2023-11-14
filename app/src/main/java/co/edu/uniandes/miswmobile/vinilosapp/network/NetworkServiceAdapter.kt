package co.edu.uniandes.miswmobile.vinilosapp.network

import android.content.Context
import android.util.Log
import co.edu.uniandes.miswmobile.vinilosapp.BuildConfig
import co.edu.uniandes.miswmobile.vinilosapp.models.Album
import co.edu.uniandes.miswmobile.vinilosapp.models.Band
import co.edu.uniandes.miswmobile.vinilosapp.models.Musician
import co.edu.uniandes.miswmobile.vinilosapp.models.Performer
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import co.edu.uniandes.miswmobile.vinilosapp.models.Album
import co.edu.uniandes.miswmobile.vinilosapp.models.Band
import co.edu.uniandes.miswmobile.vinilosapp.models.Musician
import co.edu.uniandes.miswmobile.vinilosapp.models.Performer
import com.android.volley.AuthFailureError
import com.android.volley.NetworkError
import com.android.volley.NoConnectionError
import com.android.volley.ParseError
import com.android.volley.ServerError
import com.android.volley.TimeoutError
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

open class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= BuildConfig.BASE_URL
        private var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }
    open suspend fun getAlbums() = suspendCoroutine<List<Album>> { cont ->
        requestQueue.add(
            getRequest("albums",
                { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Album>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        val album = Album(
                            albumId = item.getInt("id"),
                            name = item.getString("name"),
                            cover = item.getString("cover"),
                            releaseDate = item.getString("releaseDate"),
                            description = item.getString("description"),
                            genre = item.getString("genre"),
                            recordLabel = item.getString("recordLabel")
                        )
                        list.add(i, album) //se agrega a medida que se procesa la respuesta
                    }
                    cont.resume(list)
                },
                Response.ErrorListener {
                    cont.resumeWithException(it)
                })
        )
    }

    suspend fun getBand(
        onComplete: (resp: List<Band>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        requestQueue.add(
            getRequest("bands",
                { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Band>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        list.add(
                            i,
                            Band(
                                performerId = item.getInt("id"),
                                name = item.getString("name"),
                                image = item.getString("image"),
                                description = item.getString("description"),
                                creationDate = item.getString("creationDate")
                            )
                        )
                    }
                    cont.resume(list)
                },
                {
                    cont.resumeWithException(it)
                })
        )
    }

    open suspend fun getBand() = suspendCoroutine<List<Band>> { cont ->
        requestQueue.add(getRequest("bands",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Band>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Band(performerId = item.getInt("id"), name = item.getString("name"), image = item.getString("image"), description = item.getString("description"), creationDate = item.getString("creationDate")))
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }))
    }

    open suspend fun getMusician() = suspendCoroutine<List<Musician>> { cont ->
        requestQueue.add(getRequest("musicians",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Musician>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Musician(performerId = item.getInt("id"), name = item.getString("name"), image = item.getString("image"), description = item.getString("description"), birthDate = item.getString("birthDate")))
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }))
    }

    open suspend fun getPerformer() = suspendCoroutine<List<Performer>> { cont ->
        val list = mutableListOf<Performer>()
        requestQueue.add(getRequest("musicians",
            { response ->
                val resp = JSONArray(response)
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Musician(performerId = item.getInt("id"), name = item.getString("name"), image = item.getString("image"), description = item.getString("description"), birthDate = item.getString("birthDate")))
                }
                requestQueue.add(getRequest("bands",
                    { response ->
                        val resp = JSONArray(response)
                        for (i in 0 until resp.length()) {
                            val item = resp.getJSONObject(i)
                            list.add(i, Band(performerId = item.getInt("id"), name = item.getString("name"), image = item.getString("image"), description = item.getString("description"), creationDate = item.getString("creationDate")))
                        }
                        cont.resume(list)
                    },
                    {
                        cont.resumeWithException(it)
                    }))
            },
            {
                cont.resumeWithException(it)
            }))
    }

    open suspend fun createAlbum(
        album: Album
    ): JSONObject = suspendCoroutine { cont ->
        val body = JSONObject().apply {
            put("name", album.name)
            put("cover", album.cover)
            put("releaseDate", album.releaseDate)
            put("description", album.description)
            put("genre", album.genre)
            put("recordLabel", album.recordLabel)
        }

        val responseListener = Response.Listener<JSONObject> { response ->
            // Log de la respuesta exitosa del servidor
            Log.d("NetworkServiceAdapter", "Respuesta exitosa: $response")
            cont.resume(body)
        }

        val errorListener = Response.ErrorListener { error ->
            Log.d("NetworkServiceAdapter", "Valor enviado: $body")
            // Manejo específico de errores Volley
            if (error is NetworkError) {
                cont.resumeWithException(NetworkErrorException("Problema de red, posiblemente sin conexión"))
            } else if (error is ServerError && error.networkResponse != null && error.networkResponse.data != null) {
                val errorResponse = String(error.networkResponse.data)
                Log.e("NetworkServiceAdapter", "Error en la respuesta del servidor: $errorResponse")
                val statusCode = error.networkResponse?.statusCode ?: -1
                Log.e("NetworkServiceAdapter", "Error en el servidor, código de estado HTTP $statusCode: $error")
                cont.resumeWithException(ServerErrorException("Error en el servidor, código de estado HTTP $statusCode"))
            } else if (error is AuthFailureError) {
                cont.resumeWithException(AuthFailureErrorException("Error de autenticación, posiblemente credenciales incorrectas"))
            } else if (error is ParseError) {
                cont.resumeWithException(ParseErrorException("Error al analizar la respuesta del servidor"))
            } else if (error is NoConnectionError) {
                cont.resumeWithException(NoConnectionErrorException("No hay conexión a Internet"))
            } else if (error is TimeoutError) {
                cont.resumeWithException(TimeoutErrorException("Tiempo de espera agotado"))
            } else {
                // Otro tipo de error
                cont.resumeWithException(error)
            }
        }

        requestQueue.add(postRequest("albums", body, responseListener, errorListener))
    }

    private fun postRequest(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return JsonObjectRequest(
            Request.Method.POST,
            BASE_URL + path,
            body,
            responseListener,
            errorListener
        )
    }

    private fun getRequest(
        path: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL + path, responseListener, errorListener)
    }

    private fun putRequest(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return JsonObjectRequest(
            Request.Method.PUT,
            BASE_URL + path,
            body,
            responseListener,
            errorListener
        )
    }

    class NetworkErrorException(message: String) : Exception(message)

    class ServerErrorException(message: String) : Exception(message)

    class AuthFailureErrorException(message: String) : Exception(message)

    class ParseErrorException(message: String) : Exception(message)

    class NoConnectionErrorException(message: String) : Exception(message)

    class TimeoutErrorException(message: String) : Exception(message)

}