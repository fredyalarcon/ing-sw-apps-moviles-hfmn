package co.edu.uniandes.miswmobile.vinilosapp.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.AlbumComentarioAddFragmentBinding
import co.edu.uniandes.miswmobile.vinilosapp.models.Collector
import co.edu.uniandes.miswmobile.vinilosapp.models.Comentario
import co.edu.uniandes.miswmobile.vinilosapp.viewmodels.ComenarioCreateViewModel
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [PerformerAlbumsAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlbumComentarioAddFragment : Fragment() {

    private var _binding: AlbumComentarioAddFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ComenarioCreateViewModel
    private lateinit var navController: NavController

    private var albumId: Int? = null
    private var name: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            albumId = it.getInt("albumId")
            name = it.getString("name")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AlbumComentarioAddFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ComenarioCreateViewModel::class.java)
        val bundle = Bundle()
        albumId?.let { bundle.putInt("albumId", it) }
        name?.let { bundle.putString("name", it) }

        //acción cuando dé clik en el botón save de comentario
        binding.saveComentario.setOnClickListener {
            val descripcion = binding.comentario.text.toString()
            val rating = binding.rating.text.toString()
            val albumId = arguments?.getInt("albumId") ?: 0
            if (rating.length == 1 && rating[0] in '1'..'5') {
                // crear objeto comentario
                val comentario = Comentario(
                    description = descripcion,
                    rating = rating,
                    collector = Collector(
                        id = 100,
                        name = "",
                        telephone = "",
                        email = ""
                    )
                )
                // Llamar a la función suspendida dentro de un bloque de código suspendido
                lifecycleScope.launch {
                    viewModel.addComentarioToAlbum(comentario, albumId)
                }
                CreateComentarioDialogFragment(navController, bundle).show(
                    childFragmentManager,
                    CreateComentarioDialogFragment.TAG
                )
            }else {
            // Si el rating no es válido, mostrar un mensaje de error
            binding.rating.error = "El rating debe ser un número entre 1 y 5"
            }
        }


        binding.cancelComentario.setOnClickListener {
            navController.navigate(R.id.action_albumComentarioAddFragment_to_albumComentarioFragment, bundle)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        navController = activity.findNavController(R.id.nav_host_fragment)
        viewModel = ViewModelProvider(this, ComenarioCreateViewModel.Factory(activity.application)).get(
            ComenarioCreateViewModel::class.java
        )
        viewModel.eventNetworkError.observe(
            viewLifecycleOwner
        ) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment AlbumCreateFragment.
         */
        @JvmStatic
        fun newInstance() =
            AlbumCreateFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    class CreateComentarioDialogFragment(navController: NavController, bundle: Bundle) : DialogFragment() {

        private val nav = navController
        val bundle = bundle
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.album_added_album_success))
                .setPositiveButton(getString(R.string.action_accept)) { _, _ ->
                    nav.navigate(R.id.action_albumComentarioAddFragment_to_albumComentarioFragment, bundle)
                }
                .create()

        companion object {
            const val TAG = "CreateComentarioDialog"
        }
    }
}