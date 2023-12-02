package co.edu.uniandes.miswmobile.vinilosapp.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.core.content.res.TypedArrayUtils
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.AlbumCreateFragmentBinding
import co.edu.uniandes.miswmobile.vinilosapp.models.Album
import co.edu.uniandes.miswmobile.vinilosapp.viewmodels.AlbumCreateViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [AlbumCreateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlbumCreateFragment : Fragment() {

    private var _binding: AlbumCreateFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumCreateViewModel
    private lateinit var navController: NavController
    private lateinit var albumName: TextInputEditText
    private lateinit var albumCover: TextInputEditText
    private lateinit var albumCreateDate: TextInputEditText
    private lateinit var albumDescription: TextInputEditText
    private lateinit var albumGenre: AutoCompleteTextView
    private lateinit var albumRecord: AutoCompleteTextView
    private lateinit var albumSaveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AlbumCreateFragmentBinding.inflate(inflater, container, false)
        _binding!!.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(AlbumCreateViewModel::class.java)

        albumSaveButton = binding.saveAlbum
        albumName = binding.nombre
        albumCover = binding.cover
        albumCreateDate = binding.createDate
        albumDescription = binding.description
        albumGenre = binding.genre
        albumRecord = binding.record

        var isNameValid = false
        var isCoverValid = false
        var isCreateDateValid = false
        var isDescriptionValid = false
        var isGenreValid = false
        var isRecordValid = false

        //acción cuando dé clik en el botón save de álbum
        albumSaveButton.setOnClickListener {
            val name = albumName.text.toString()
            val cover = albumCover.text.toString()
            val releaseDate = albumCreateDate.text.toString()
            val description = albumDescription.text.toString()
            val genre = albumGenre.text.toString()
            val recordLabel = albumRecord.text.toString()

            // crear objeto álbum
            val album = Album(
                name = name,
                cover = cover,
                releaseDate = releaseDate,
                description = description,
                genre = genre,
                recordLabel = recordLabel
            )
            // Llamar a la función suspendida dentro de un bloque de código suspendido
            lifecycleScope.launch {
                viewModel.createAlbum(album)
            }
            CreateAlbumDialogFragment(navController).show(
                childFragmentManager,
                CreateAlbumDialogFragment.TAG
            )
        }

        viewModel.genres.observe(viewLifecycleOwner) { genres ->
            val genresField = binding.genre
            val genresAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                genres.orEmpty()
            )
            genresField.setAdapter(genresAdapter)
        }

        viewModel.recordLabels.observe(viewLifecycleOwner) { recordLabels ->
            val recordsLabelsField = binding.record
            val recordLabelsAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                recordLabels.orEmpty()
            )
            recordsLabelsField.setAdapter(recordLabelsAdapter)
        }

        binding.cancelAlbum.setOnClickListener {
            navController.navigate(R.id.action_albumCreateFragment_to_albumFragment)
        }

        class ValidationTextWatcher(private val validationCallBack: () -> Unit): TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                validationCallBack.invoke()
                albumSaveButton.isEnabled = isNameValid && isCoverValid && isCreateDateValid &&
                        isDescriptionValid && isGenreValid && isRecordValid
                albumSaveButton.alpha= 1f
            }

        }

        albumName.addTextChangedListener(
            ValidationTextWatcher {
                isNameValid = albumName.text.toString().isNotEmpty()
            }
        )

        albumCover.addTextChangedListener(
            ValidationTextWatcher {
                isCoverValid = albumCover.text.toString().isNotEmpty()
            }
        )

        albumCreateDate.addTextChangedListener(
            ValidationTextWatcher {
                isCreateDateValid = albumCreateDate.text.toString().isNotEmpty()
            }
        )

        albumDescription.addTextChangedListener(
            ValidationTextWatcher {
                isDescriptionValid = albumDescription.text.toString().isNotEmpty()
            }
        )

        albumGenre.addTextChangedListener(
            ValidationTextWatcher {
                isGenreValid = albumGenre.text.toString().isNotEmpty()
            }
        )

        albumRecord.addTextChangedListener(
            ValidationTextWatcher {
                isRecordValid = albumRecord.text.toString().isNotEmpty()
            }
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        navController = activity.findNavController(R.id.nav_host_fragment)
        viewModel = ViewModelProvider(this, AlbumCreateViewModel.Factory(activity.application)).get(
            AlbumCreateViewModel::class.java
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

    class CreateAlbumDialogFragment(navController: NavController) : DialogFragment() {

        private val nav = navController
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.album_added_album_success))
                .setPositiveButton(getString(R.string.action_accept)) { _, _ ->
                    nav.navigate(R.id.action_albumCreateFragment_to_albumFragment)
                }
                .create()

        companion object {
            const val TAG = "CreateAlbumDialog"
        }
    }
}
