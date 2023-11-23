package co.edu.uniandes.miswmobile.vinilosapp.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.PerformerAlbumsFragmentBinding
import co.edu.uniandes.miswmobile.vinilosapp.models.Album
import co.edu.uniandes.miswmobile.vinilosapp.ui.adapters.AlbumsAdapter
import co.edu.uniandes.miswmobile.vinilosapp.viewmodels.AlbumViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [PerformerAlbumsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PerformerAlbumsFragment : Fragment() {

    private var performerId: Int? = null
    private var instanceType: String? = null
    private var name: String? = null

    private var _binding: PerformerAlbumsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumsAdapter? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            performerId = it.getInt("performerId")
            instanceType = it.getString("instanceType")
            name = it.getString("name")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val addAlbum = menu.findItem(R.id.add_album_to_artist)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        var preferences: SharedPreferences = activity.getSharedPreferences("co.edu.uniandes.miswmobile.vinilosapp", Context.MODE_PRIVATE);
        val collector = preferences.getString("collector", "");
        addAlbum.isVisible = !collector.isNullOrEmpty()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_album_to_artist -> {
                val bundle = Bundle()
                performerId?.let { bundle.putInt("performerId", it) }
                instanceType?.let { bundle.putString("instanceType", it) }
                name?.let { bundle.putString("name", it) }
                navController.navigate(R.id.action_artistAlbumsFragment_to_performerAlbumsAddFragment, bundle)
                true
            }
            R.id.go_to_menu -> {
                navController.navigate(R.id.action_performerAlbumsFragment_to_menuFragment)
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
        _binding = PerformerAlbumsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = AlbumsAdapter()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.albumsRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        viewModel = ViewModelProvider(activity, AlbumViewModel.Factory(activity.application)).get(
            AlbumViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        navController = activity.findNavController(R.id.nav_host_fragment)
        progressBar = activity.findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        viewModel.albums.observe(viewLifecycleOwner, Observer<List<Album>> {
            if (!instanceType.isNullOrEmpty() && performerId != null) {
                viewModel.getPerformerAlbums(instanceType!!, performerId!!)
            }
        })

        viewModel.performerAlbums.observe(viewLifecycleOwner, Observer<List<Album>> {
            it.apply {
                viewModelAdapter!!.albums = this
                if (this.isEmpty()) {
                    Toast.makeText(activity, getString(R.string.album_there_no_albums), Toast.LENGTH_LONG).show()
                }
                progressBar.visibility = View.INVISIBLE
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        val albumsTitle = resources.getString(R.string.albums)
        (context as AppCompatActivity).supportActionBar!!.title = "$name / $albumsTitle"
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
         * @param performerId performer Id.
         * @param instanceType performer type.
         * @param name performer name.
         *
         *
         * @return A new instance of fragment performerDetail.
         */
        @JvmStatic
        fun newInstance(performerId: String, instanceType: String, name: String) =
            PerformerDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("performerId", performerId)
                    putString("instanceType", instanceType)
                    putString("name", name)
                }
            }
    }
}