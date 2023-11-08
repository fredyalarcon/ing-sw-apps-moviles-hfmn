package co.edu.uniandes.miswmobile.vinilosapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.PerformerFragmentBinding
import co.edu.uniandes.miswmobile.vinilosapp.models.Performer
import co.edu.uniandes.miswmobile.vinilosapp.ui.adapters.PerformerAdapter
import co.edu.uniandes.miswmobile.vinilosapp.viewmodels.PerformerViewModel


class PerformerFragment : Fragment() {

    private var _binding: PerformerFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: PerformerViewModel
    private var viewModelAdapter: PerformerAdapter? = null
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PerformerFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = PerformerAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.artistsRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        activity.actionBar?.title = getString(R.string.artists)
        progressBar = activity.findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        viewModel = ViewModelProvider(this, PerformerViewModel.Factory(activity.application)).get(
            PerformerViewModel::class.java)

        viewModel.artists.observe(viewLifecycleOwner, Observer<List<Performer>> {
            it.apply {
                viewModelAdapter!!.performers = this
                if (isEmpty()) {
                    Toast.makeText(activity, getString(R.string.artist_there_no_artists), Toast.LENGTH_LONG).show()
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