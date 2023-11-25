package co.edu.uniandes.miswmobile.vinilosapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import co.edu.uniandes.miswmobile.vinilosapp.R

/**
 * A simple [Fragment] subclass.
 * Use the [TrackCreateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrackCreateFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.fragment_track_create, container, false)

        val duracion: TextView = view.findViewById(R.id.duration_track)
        // minutos NumberPicker
        val minutesPicker: NumberPicker = view.findViewById(R.id.minutesPicker)
        // segundos NumberPicker
        val segundosPicker: NumberPicker = view.findViewById(R.id.secondsPicker)

        // Establecer el valor máximo y mínimo
        minutesPicker.maxValue = 59
        minutesPicker.minValue = 0
        segundosPicker.maxValue = 59
        segundosPicker.minValue = 0

        // Configurar un listener para minutosPicker
        minutesPicker.setOnValueChangedListener { _, _, newVal ->
            actualizarDuracion(duracion, newVal, segundosPicker.value)
        }

        // Configurar un listener para segundosPicker
        segundosPicker.setOnValueChangedListener { _, _, newVal ->
            actualizarDuracion(duracion, minutesPicker.value, newVal)
        }

        // Inicializar el TextView con los valores iniciales
        actualizarDuracion(duracion, minutesPicker.value, segundosPicker.value)

        return view
    }

    private fun actualizarDuracion(duracion: TextView, minutos: Int, segundos: Int) {
        duracion.text = String.format("%02d:%02d", minutos, segundos)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TrackCreateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TrackCreateFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}