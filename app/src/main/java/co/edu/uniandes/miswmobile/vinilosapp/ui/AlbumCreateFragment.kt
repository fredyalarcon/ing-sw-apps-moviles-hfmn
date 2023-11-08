package co.edu.uniandes.miswmobile.vinilosapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.edu.uniandes.miswmobile.vinilosapp.databinding.AlbumCreateFragmentBinding

/**
 * A simple [Fragment] subclass.
 * Use the [AlbumCreateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlbumCreateFragment : Fragment() {

    private var _binding: AlbumCreateFragmentBinding? = null
    private val binding get() = _binding!!

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
        return binding.root
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
}