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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.PerformerAlbumsAddFragmentBinding
import co.edu.uniandes.miswmobile.vinilosapp.viewmodels.AlbumViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [PerformerAlbumsAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PerformerAlbumsAddFragment : Fragment() {

    private var performerId: Int? = null
    private var instanceType: String? = null
    private var name: String? = null

    private var selectedAlbumId: Int? = null
    private var _albumsToAdd: HashMap<Int?, String>? = null
    private var _binding: PerformerAlbumsAddFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumViewModel
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            performerId = it.getInt("performerId")
            instanceType = it.getString("instanceType")
            name = it.getString("name")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        viewModel = ViewModelProvider(activity, AlbumViewModel.Factory(activity.application)).get(
            AlbumViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PerformerAlbumsAddFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        navController = activity.findNavController(R.id.nav_host_fragment)
        binding.cancelAlbum.setOnClickListener {
            navController.navigate(R.id.action_performerAlbumsAddFragment_to_artistAlbumsFragment)
        }

        binding.saveAlbum.setOnClickListener {
            if (!instanceType.isNullOrEmpty() && selectedAlbumId != null && performerId != null) {
                viewModel.addAlbumToPerformer(instanceType!!, performerId!!, selectedAlbumId!!)

                val bundle = Bundle()
                performerId?.let { bundle.putInt("performerId", it) }
                instanceType?.let { bundle.putString("instanceType", it) }
                name?.let { bundle.putString("name", it) }

                PerformerAlbumsAddFragment.CreateAlbumDialogFragment(navController, bundle).show(
                    childFragmentManager,
                    PerformerAlbumsAddFragment.CreateAlbumDialogFragment.TAG
                )
            }
        }

        viewModel.performerAlbumsToAdd.observe(viewLifecycleOwner, Observer<HashMap<Int?, String>> {
            it.apply {
                _albumsToAdd = it
                val values = _albumsToAdd?.values?.toTypedArray() ?: arrayOf<String>()
                val autoComplete = _binding?.album
                if (autoComplete != null) {
                    val adapter = ArrayAdapter(
                        context as MainActivity,
                        android.R.layout.simple_spinner_item,
                        values
                    )
                    autoComplete.setAdapter(adapter)
                }
            }
        })


        // access the spinner
        val autoComplete = _binding?.album
        if (autoComplete != null) {
            autoComplete.onItemClickListener = object :
                AdapterView.OnItemClickListener {
                override fun onItemClick(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    selectedAlbumId = _albumsToAdd?.keys?.toTypedArray()?.get(position)
                }
            }
        }
    }

    class CreateAlbumDialogFragment(navController: NavController, bundle: Bundle) : DialogFragment() {

        private val nav = navController
        private val bundle = bundle

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.album_added_album_success))
                .setPositiveButton(getString(R.string.action_accept)) { _, _ ->
                    nav.navigate(R.id.action_performerAlbumsAddFragment_to_artistAlbumsFragment, bundle)
                }
                .create()

        companion object {
            const val TAG = "AddAlbumDialog"
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