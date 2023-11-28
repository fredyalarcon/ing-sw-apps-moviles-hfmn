package co.edu.uniandes.miswmobile.vinilosapp.network

import com.android.volley.RequestQueue
import kotlinx.coroutines.ExperimentalCoroutinesApi


import org.junit.Before
import org.junit.Test
import org.mockito.Mock


@ExperimentalCoroutinesApi
class NetworkServiceAdapterTest {

    @Mock
    private lateinit var requestQueue: RequestQueue

    private var mockResponse: String = ""

    @Before
    fun setUp() {
        mockResponse = """
            [
                {"id": 1, "name": "Track 1", "duration": "3:20"},
                {"id": 2, "name": "Track 2", "duration": "4:15"},
                {"id": 3, "name": "Track 3", "duration": "2:50"}
            ]
        """.trimIndent()


    }

    @Test
    fun getTracks() {
    }
}