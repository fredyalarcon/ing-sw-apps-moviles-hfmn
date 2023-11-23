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
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.ComentarioFragmentBinding
import co.edu.uniandes.miswmobile.vinilosapp.databinding.ComentarioItemBinding
import co.edu.uniandes.miswmobile.vinilosapp.databinding.FragmentAlbumTrackListBinding
import co.edu.uniandes.miswmobile.vinilosapp.models.Comentario
import co.edu.uniandes.miswmobile.vinilosapp.models.Track
import co.edu.uniandes.miswmobile.vinilosapp.ui.adapters.AlbumTrackAdapter
import co.edu.uniandes.miswmobile.vinilosapp.ui.adapters.ComentarioAdapter
import co.edu.uniandes.miswmobile.vinilosapp.viewmodels.ComentarioViewModel
import co.edu.uniandes.miswmobile.vinilosapp.viewmodels.TrackViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [Comentario_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Comentario_Fragment : Fragment() {

    private var albumId: Int? = null
    private var _binding: ComentarioFragmentBinding? = null//*
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ComentarioViewModel
    private var viewModelAdapter: ComentarioAdapter? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            albumId = it.getInt("albumId")
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.add_track)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        var preferences: SharedPreferences = activity.getSharedPreferences("co.edu.uniandes.miswmobile.vinilosapp", Context.MODE_PRIVATE);
        val collector = preferences.getString("collector", "");
        item.isVisible = !collector.isNullOrEmpty()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_track -> {
                //cambiar la navegación al formulario de crear canción
                //navController.navigate(R.id.action_albumFragment_to_albumCreateFragment)
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
        _binding = ComentarioFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = ComentarioAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.albumsTrackRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        navController = activity.findNavController(R.id.nav_host_fragment)
        progressBar = activity.findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        val args: AlbumTrackListFragmentArgs by navArgs()
        Log.d("Args", args.albumId.toString())

        viewModel = ViewModelProvider(activity, ComentarioViewModel.Factory(activity.application, args.albumId)).get(
            ComentarioViewModel::class.java)
        viewModel.comentarios.observe(viewLifecycleOwner, Observer<List<Comentario>> {
            it.apply {
                viewModelAdapter!!.comentarios = this
                if (this.isEmpty()) {
                    Toast.makeText(activity, getString(R.string.album_there_no_tracks), Toast.LENGTH_LONG).show()
                }
                progressBar.visibility = View.INVISIBLE
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

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
}