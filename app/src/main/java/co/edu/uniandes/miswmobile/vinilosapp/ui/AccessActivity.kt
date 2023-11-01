package co.edu.uniandes.miswmobile.vinilosapp.ui

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vinyls_jetpack_application.databinding.ActivityAccessBinding
import com.example.vinyls_jetpack_application.databinding.AlbumFragmentBinding

public class AccessActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityAccessBinding
    private val binding get() = _binding!!
    private lateinit var buttonVisitor: Button
    private lateinit var buttonCollector: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAccessBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        textView = binding.textView
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