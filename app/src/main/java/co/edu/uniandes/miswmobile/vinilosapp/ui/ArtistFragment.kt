package co.edu.uniandes.miswmobile.vinilosapp.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.ArtistFragmentBinding
import co.edu.uniandes.miswmobile.vinilosapp.viewmodels.ArtistViewModel


class ArtistFragment : Fragment() {

    private var _binding: ArtistFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ArtistViewModel
    //todo private var viewModelAdapter: ArtistAdater? = null
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ArtistFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        // todo viewModelAdapter = ArtistAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.artistsRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        //todo recyclerView.adapter = viewModelAdapter
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        activity.actionBar?.title = getString(R.string.artists)
        progressBar = activity.findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        viewModel = ViewModelProvider(this, ArtistViewModel.Factory(activity.application)).get(
            ArtistViewModel::class.java)
        //todo
        /*
        viewModel.artists.observe(viewLifecycleOwner, Observer<List<Artist>> {
            it.apply {
                viewModelAdapter!!.artists = this
                if (isEmpty()) {
                    Toast.makeText(activity, getString(R.string.artist_there_no_artists), Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.INVISIBLE
                }
            }
        })
        */
        //todo
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