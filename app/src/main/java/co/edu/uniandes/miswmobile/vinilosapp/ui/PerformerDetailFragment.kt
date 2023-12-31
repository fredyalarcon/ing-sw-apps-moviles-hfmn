package co.edu.uniandes.miswmobile.vinilosapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.PerformerDetailFragmentBinding
import co.edu.uniandes.miswmobile.vinilosapp.models.Band
import co.edu.uniandes.miswmobile.vinilosapp.models.Musician
import co.edu.uniandes.miswmobile.vinilosapp.models.Performer
import co.edu.uniandes.miswmobile.vinilosapp.viewmodels.PerformerViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PerformerDetailFragment : Fragment() {

    private var performerId: Int? = null
    private var instanceType: String? = null
    private var name: String? = null


    private var _binding: PerformerDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PerformerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            performerId = it.getInt("performerId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PerformerDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        val buttonAlbums = binding.buttonAlbums
        buttonAlbums?.setOnClickListener(
            View.OnClickListener {
                // Get the navigation host fragment from this Activity
                val navController = activity.findNavController(R.id.nav_host_fragment)
                // Instantiate the navController using the NavHostFragment
                val bundle = Bundle()
                performerId?.let { bundle.putInt("performerId", it) }
                instanceType?.let { bundle.putString("instanceType", it) }
                name?.let { bundle.putString("name", it) }
                navController.navigate(R.id.action_performerDetailFragment_to_artistAlbumsFragment, bundle)
            }
        )

        viewModel = ViewModelProvider(activity, PerformerViewModel.Factory(activity.application)).get(
            PerformerViewModel::class.java)
        bind(viewModel.getPerformer(performerId))


        return view
    }

    fun bind(performer: Performer?) {
        if (performer != null && _binding != null) {
            _binding?.performer = performer
            name = performer.name
            if (performer is Musician) {
                instanceType = "musicians"
                _binding?.musician = performer as Musician?
                //_binding?.musician?.birthDate = formatDate(performer.birthDate)
                _binding?.textView7?.text = formatDate(performer.birthDate)
                _binding?.textView11?.text = getString(R.string.birthDate)
            }
            if (performer is Band) {
                instanceType = "bands"
                _binding?.band = performer as Band?
                //_binding?.band?.creationDate = formatDate(performer.creationDate)
                _binding?.textView7?.text = formatDate(performer.creationDate)
                _binding?.textView11?.text = getString(R.string.createDate)
            }

            Glide.with(this)
                .load(performer.image.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.progress_animation)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_broken_image))
                .into(binding.imageView)

            Glide.with(this)
                .load(performer.image.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.progress_animation)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_broken_image))
                .into(binding.imageView2)

        }
    }

    fun formatDate(dateString: String?): String {
        if (dateString.isNullOrEmpty()) {
            return ""
        }

        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

        return try {
            val date: Date = inputFormat.parse(dateString)
            outputFormat.format(date)
        } catch (e: Exception) {
            dateString
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param performerId performer Id.
         * @return A new instance of fragment performerDetail.
         */
        @JvmStatic
        fun newInstance(performerId: String) =
            PerformerDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("performerId", performerId)
                }
            }
    }
}