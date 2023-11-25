package co.edu.uniandes.miswmobile.vinilosapp.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.AlbumComentarioAddFragmentBinding
import co.edu.uniandes.miswmobile.vinilosapp.databinding.PerformerAlbumsAddFragmentBinding
import co.edu.uniandes.miswmobile.vinilosapp.models.Album
import co.edu.uniandes.miswmobile.vinilosapp.models.Collector
import co.edu.uniandes.miswmobile.vinilosapp.models.Comentario
import co.edu.uniandes.miswmobile.vinilosapp.viewmodels.AlbumViewModel
import co.edu.uniandes.miswmobile.vinilosapp.viewmodels.ComentarioViewModel
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [PerformerAlbumsAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlbumComentarioAddFragment : Fragment() {

    private var performerId: Int? = null
    private var instanceType: String? = null
    private var name: String? = null
    private var albumId: Int? = null

    private var selectedAlbumId: Int? = null
    private var _albumsToAdd: HashMap<Int?, String>? = null
    private var _binding: AlbumComentarioAddFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ComentarioViewModel
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            performerId = it.getInt("performerId")
            instanceType = it.getString("instanceType")
            name = it.getString("name")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AlbumComentarioAddFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        navController = activity.findNavController(R.id.nav_host_fragment)
        binding.cancelComentario.setOnClickListener {
            navController.navigate(R.id.action_albumComentarioAddFragment_to_albumComentarioFragment)
        }

        binding.saveComentario.setOnClickListener {
            val raiting = binding.raiting.text.toString()
            val description = binding.comentario.text.toString()

            // crear objeto álbum
            /*val comentario = Comentario(
                description = description,
                rating = raiting,
                collector = Collector
            )
            lifecycleScope.launch {
                viewModel.addComentarioToAlbum(comentario, albumId)
            }*/
            val albumId = arguments?.getInt("albumId") ?: 0
            val name = arguments?.getString("name")
            val bundle = Bundle()
            albumId?.let { bundle.putInt("albumId", it) }
            name?.let { bundle.putString("name", it) }
            AlbumComentarioAddFragment.CreateComentarioDialogFragment(navController, bundle).show(
                childFragmentManager,
                AlbumCreateFragment.CreateAlbumDialogFragment.TAG
            )
        }

    }

    class CreateComentarioDialogFragment(navController: NavController, bundle: Bundle) : DialogFragment() {

        private val nav = navController
        private val bundle = bundle

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.comentario_added_album_success))
                .setPositiveButton(getString(R.string.action_accept)) { _, _ ->
                    nav.navigate(R.id.action_albumComentarioAddFragment_to_albumComentarioFragment, bundle)
                }
                .create()

        companion object {
            const val TAG = "AddComentarioDialog"
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param performerId performer Id.
         * @param instanceType performer type.
         * @param name performer name.
         *
         * @return A new instance of fragment PerformerAlbumsAddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(performerId: String, instanceType: String, name: String) =
            PerformerAlbumsAddFragment().apply {
                arguments = Bundle().apply {
                    putString("performerId", performerId)
                    putString("instanceType", instanceType)
                    putString("name", name)
                }
            }
    }
}