package co.edu.uniandes.miswmobile.vinilosapp.network

import android.content.Context
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
import org.json.JSONArray
import org.json.JSONObject
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
                        list.add(
                            i,
                            Album(
                                albumId = item.getInt("id"),
                                name = item.getString("name"),
                                cover = item.getString("cover"),
                                recordLabel = item.getString("recordLabel"),
                                releaseDate = item.getString("releaseDate"),
                                genre = item.getString("genre"),
                                description = item.getString("description")
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

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL +path, responseListener,errorListener)
    }
    private fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.POST, BASE_URL +path, body, responseListener, errorListener)
    }
    private fun putRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL +path, body, responseListener, errorListener)
    }
}