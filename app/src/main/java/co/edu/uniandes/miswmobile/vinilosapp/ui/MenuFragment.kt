package co.edu.uniandes.miswmobile.vinilosapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.MenuFragmentBinding

/**
 * A simple [Fragment] subclass.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment() {
    private var _binding: MenuFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var buttonAlbums: LinearLayout
    private lateinit var buttonArtist: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        _binding = MenuFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        buttonAlbums = binding.buttonAlbums
        buttonAlbums?.setOnClickListener(
            View.OnClickListener {
                // Get the navigation host fragment from this Activity
                val navController = activity.findNavController(R.id.nav_host_fragment)
                // Instantiate the navController using the NavHostFragment
                navController.navigate(R.id.action_menuFragment_to_albumFragment)
            }
        )

        buttonArtist = binding.buttonArtists
        buttonArtist.setOnClickListener(
            View.OnClickListener {
                val navController = activity.findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.action_menuFragment_to_artistFragment)
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}