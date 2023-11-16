package co.edu.uniandes.miswmobile.vinilosapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.AlbumDetailFragmentBinding
import co.edu.uniandes.miswmobile.vinilosapp.models.Album
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
    private var albumId: Int? = null
    private var _binding: AlbumDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumViewModel

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
        bind(viewModel.getAlbum(albumId))


        return view
    }

    fun bind(album: Album?) {
        if (album != null && _binding != null) {
            _binding?.album = album
            _binding?.progressBarAlbumItem?.let { progressBar ->
                Glide.with(this)
                    .load(album.cover.toUri().buildUpon().scheme("https").build())
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.progress_animation)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_broken_image))
                    .into(binding.imageView)

                Glide.with(this)
                    .load(album.cover.toUri().buildUpon().scheme("https").build())
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.progress_animation)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_broken_image))
                    .into(binding.imageView2)
            }
        }
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