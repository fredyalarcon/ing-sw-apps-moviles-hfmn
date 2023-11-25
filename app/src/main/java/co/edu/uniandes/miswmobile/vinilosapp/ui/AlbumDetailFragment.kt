package co.edu.uniandes.miswmobile.vinilosapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.AlbumDetailFragmentBinding
import co.edu.uniandes.miswmobile.vinilosapp.models.Album
import co.edu.uniandes.miswmobile.vinilosapp.models.Performer
import co.edu.uniandes.miswmobile.vinilosapp.viewmodels.AlbumCreateViewModel
import co.edu.uniandes.miswmobile.vinilosapp.viewmodels.AlbumViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey


/**
 * A simple [Fragment] subclass.
 * Use the [AlbumDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlbumDetailFragment : Fragment() {
    private var name: String? = null

    private var albumId: Int? = null
    private var _binding: AlbumDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumViewModel
    private var lastId: Int? = null
    private lateinit var navController: NavController

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
        _binding = AlbumDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(activity, AlbumViewModel.Factory(activity.application)).get(
            AlbumViewModel::class.java)
        viewModel.album.observe(viewLifecycleOwner, Observer<Album> {
            it.apply {
                bind(it)
            }
        })
        if (albumId != null) {
            viewModel.getAlbum(albumId!!)
        }


        binding.buttonSongs.setOnClickListener {
            val action = AlbumDetailFragmentDirections.actionAlbumDetailToAlbumTrackListFragment(albumId!!)
            navController.navigate(action)
        }
        val buttonComents = binding.buttonComments
        buttonComents ?.setOnClickListener(
            View.OnClickListener {
                // Get the navigation host fragment from this Activity
                val navController = activity.findNavController(R.id.nav_host_fragment)
                // Instantiate the navController using the NavHostFragment
                val bundle = Bundle()
                albumId?.let { bundle.putInt("albumId", it) }
                name?.let { bundle.putString("name", it) }
                navController.navigate(R.id.action_albumDetail_to_albumComentarioFragment, bundle)
            }
        )
        return view
    }

    fun bind(album: Album?) {
        lastId = album?.albumId
        Log.d("AlbumDetailFragment", "bind called with album: $album")
        if (album != null && _binding != null) {
            _binding?.album = album
            name = album.name
            Glide.with(this)
                .load(album.cover.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.progress_animation)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_broken_image)
                )
                .into(binding.imageView)

            Glide.with(this)
                .load(album.cover.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.progress_animation)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_broken_image)
                )
                .into(binding.imageView2)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        navController = activity.findNavController(R.id.nav_host_fragment)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param albumId Album Id.
         * @return A new instance of fragment AlbumDetail.
         */
        @JvmStatic
        fun newInstance(albumId: String) =
            AlbumDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("albumId", albumId)
                }
            }
    }
}