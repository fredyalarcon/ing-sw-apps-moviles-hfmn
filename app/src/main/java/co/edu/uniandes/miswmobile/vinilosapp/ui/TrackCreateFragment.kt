package co.edu.uniandes.miswmobile.vinilosapp.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.FragmentTrackCreateBinding
import co.edu.uniandes.miswmobile.vinilosapp.models.Track
import co.edu.uniandes.miswmobile.vinilosapp.viewmodels.TrackCreateViewModel
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [TrackCreateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrackCreateFragment : Fragment() {

    private var _binding: FragmentTrackCreateBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TrackCreateViewModel
    private lateinit var navController: NavController
    private var albumId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            albumId = it.getInt("albumId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragmento
        _binding = FragmentTrackCreateBinding.inflate(inflater, container, false)
        _binding!!.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(TrackCreateViewModel::class.java)

        val duracion: TextView = binding.durationTrack
        // minutos NumberPicker
        val minutesPicker: NumberPicker = binding.minutesPicker
        // segundos NumberPicker
        val segundosPicker: NumberPicker = binding.secondsPicker

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

        binding.saveTrack.setOnClickListener {
            val track =
                Track(name = binding.name.text.toString(), duration = binding.durationTrack.text.toString())

            val args: TrackCreateFragmentArgs by navArgs()
            lifecycleScope.launch {
                viewModel.addTrack(track, args.albumId)
            }
            CreateTrackDialogFragment(navController, args.albumId).show(
                childFragmentManager,
                CreateTrackDialogFragment.TAG
            )
        }

        binding.cancelTrack.setOnClickListener {
            navController.navigate(R.id.action_trackCreateFragment_to_albumTrackListFragment)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        navController = activity.findNavController(R.id.nav_host_fragment)

        viewModel = ViewModelProvider(this, TrackCreateViewModel.Factory(activity.application)).get(
            TrackCreateViewModel::class.java
        )
        viewModel.eventNetworkError.observe(
            viewLifecycleOwner
        ) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }

    }

    private fun actualizarDuracion(duracion: TextView, minutos: Int, segundos: Int) {
        duracion.text = String.format("%02d:%02d", minutos, segundos)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TrackCreateFragment.
         */
        @JvmStatic
        fun newInstance() =
            TrackCreateFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    class CreateTrackDialogFragment(navController: NavController, _albumId: Int) : DialogFragment() {

        private val nav = navController
        private val albumId = _albumId

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.track_added_success))
                .setPositiveButton(getString(R.string.action_accept)) { _, _ ->
                    val action = TrackCreateFragmentDirections.actionTrackCreateFragmentToAlbumTrackListFragment(albumId!!)
                    nav.navigate(action)
                }
                .create()

        companion object {
            const val TAG = "CreateTrackDialog"
        }
    }
}