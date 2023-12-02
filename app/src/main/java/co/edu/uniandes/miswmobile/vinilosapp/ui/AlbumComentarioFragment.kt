package co.edu.uniandes.miswmobile.vinilosapp.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.AlbumComentarioFragmentBinding
import co.edu.uniandes.miswmobile.vinilosapp.models.Album
import co.edu.uniandes.miswmobile.vinilosapp.models.Comentario
import co.edu.uniandes.miswmobile.vinilosapp.models.Performer
import co.edu.uniandes.miswmobile.vinilosapp.ui.adapters.ComentarioAdapter
import co.edu.uniandes.miswmobile.vinilosapp.viewmodels.AlbumViewModel
import co.edu.uniandes.miswmobile.vinilosapp.viewmodels.ComentarioViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [Comentario_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlbumComentarioFragment : Fragment() {

    private var albumId: Int? = null
    private var _binding: AlbumComentarioFragmentBinding? = null//*
    private var name: String? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ComentarioViewModel
    private var viewModelAdapter: ComentarioAdapter? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            albumId = it.getInt("albumId")
            name = it.getString("name")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val addComentario = menu.findItem(R.id.add_comentario)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        var preferences: SharedPreferences = activity.getSharedPreferences("co.edu.uniandes.miswmobile.vinilosapp", Context.MODE_PRIVATE);
        val collector = preferences.getString("collector", "");
        addComentario.isVisible = !collector.isNullOrEmpty()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_comentario -> {
                val bundle = Bundle()
                albumId?.let { bundle.putInt("albumId", it) }
                name?.let { bundle.putString("name", it) }
                navController.navigate(R.id.action_albumComentarioFragment_to_albumComentarioAddFragment, bundle)
                true
            }
            R.id.go_to_menu -> {
                navController.navigate(R.id.action_albumComentarioFragment_to_menuFragment)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AlbumComentarioFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = ComentarioAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.albumsRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

        /*val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val albumId = arguments?.getInt("albumId") ?: 0
        val viewModelFactory = ComentarioViewModel.Factory(activity.application, albumId)
        viewModel = ViewModelProvider(activity, viewModelFactory)
            .get(ComentarioViewModel::class.java)*/
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        navController = activity.findNavController(R.id.nav_host_fragment)
        progressBar = activity.findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        val albumId = arguments?.getInt("albumId") ?: 0
        val viewModelFactory = ComentarioViewModel.Factory(activity.application, albumId)
        viewModel = ViewModelProvider(activity, viewModelFactory)
            .get(ComentarioViewModel::class.java)

        viewModel.comentarios.observe(viewLifecycleOwner, Observer<List<Comentario>> {
            it.apply {
                viewModelAdapter!!.comentarios = this
                if (this.isEmpty()) {
                    Toast.makeText(activity, getString(R.string.album_there_no_comentary), Toast.LENGTH_LONG).show()
                }
                progressBar.visibility = View.INVISIBLE
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        val comentarioTitle = resources.getString(R.string.comments)
        (context as AppCompatActivity).supportActionBar!!.title = "$name / $comentarioTitle"

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param albumId album Id.
         * @param name performer name.
         *
         *
         * @return A new instance of fragment performerDetail.
         */
        @JvmStatic
        fun newInstance(albumId: String, name: String) =
            AlbumComentarioFragment().apply {
                arguments = Bundle().apply {
                    putString("albumId", albumId)
                    putString("name", name)
                }
            }
    }
}