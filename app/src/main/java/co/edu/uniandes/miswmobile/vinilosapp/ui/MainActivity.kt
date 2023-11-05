package co.edu.uniandes.miswmobile.vinilosapp.ui

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import co.edu.uniandes.miswmobile.vinilosapp.databinding.ActivityMainBinding
import co.edu.uniandes.miswmobile.vinilosapp.R
import com.squareup.picasso.LruCache
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the navigation host fragment from this Activity
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // Instantiate the navController using the NavHostFragment
        navController = navHostFragment.navController
        // Make sure actions in the ActionBar get propagated to the NavController
        setSupportActionBar(findViewById(R.id.my_toolbar))
        setupActionBarWithNavController(navController)

        val okHttpClient = OkHttpClient.Builder()
            // Configuración de OkHttpClient si es necesario
            .build()

        val picasso = Picasso.Builder(this)
            .memoryCache(LruCache(this))
            .downloader(OkHttp3Downloader(okHttpClient))
            .defaultBitmapConfig(Bitmap.Config.RGB_565)
            .indicatorsEnabled(true) // Habilita indicadores
            .loggingEnabled(true)    // Habilita registros
            .build()

        Picasso.setSingletonInstance(picasso)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}