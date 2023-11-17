package co.edu.uniandes.miswmobile.vinilosapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import co.edu.uniandes.miswmobile.vinilosapp.databinding.ActivityAccessBinding
import co.edu.uniandes.miswmobile.vinilosapp.R

class AccessActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityAccessBinding
    private val binding get() = _binding!!
    private lateinit var buttonVisitor: LinearLayout
    private lateinit var buttonCollector: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAccessBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _binding.toolbar2.title = getString(R.string.welcome)

        buttonVisitor = binding.buttonVisitor
        buttonCollector = binding.buttonCollector
        buttonVisitor?.setOnClickListener(
            View.OnClickListener {
                navigateToMainActivitiy()
            }
        )
    }

    private fun navigateToMainActivitiy() {
        val i = Intent(this@AccessActivity, MainActivity::class.java)
        startActivity(i)
    }
}