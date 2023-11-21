package co.edu.uniandes.miswmobile.vinilosapp.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
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
                var preferences: SharedPreferences = getSharedPreferences(
                    "co.edu.uniandes.miswmobile.vinilosapp",
                    Context.MODE_PRIVATE
                );
                preferences.edit().putString("collector", "").commit()
                navigateToMainActivitiy()
            }
        )

        val languages = resources.getStringArray(R.array.collectors)

        // access the spinner
        val spinner = _binding.spinner
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, languages)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {

                    if (position > 0) {
                        var preferences: SharedPreferences = getSharedPreferences(
                            "co.edu.uniandes.miswmobile.vinilosapp",
                            Context.MODE_PRIVATE
                        );
                        preferences.edit().putString("collector", languages.get(position)).commit()

                        navigateToMainActivitiy()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }

    private fun navigateToMainActivitiy() {
        val i = Intent(this@AccessActivity, MainActivity::class.java)
        startActivity(i)
    }
}