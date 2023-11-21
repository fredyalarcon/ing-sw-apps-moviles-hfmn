package co.edu.uniandes.miswmobile.vinilosapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import co.edu.uniandes.miswmobile.vinilosapp.databinding.ActivityMainBinding
import co.edu.uniandes.miswmobile.vinilosapp.R

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    @SuppressLint("SetTextI18n")
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

        var preferences: SharedPreferences = getSharedPreferences("co.edu.uniandes.miswmobile.vinilosapp", Context.MODE_PRIVATE);
        val collector = preferences.getString("collector", "");

        if (collector.isNullOrEmpty()) {
            binding.textCollector.text = resources.getString(R.string.visitor)
        } else {
            val user = resources.getString(R.string.user)
            binding.textCollector.text = "$user $collector"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}