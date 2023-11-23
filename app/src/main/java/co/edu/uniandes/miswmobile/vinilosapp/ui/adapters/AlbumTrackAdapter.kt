package co.edu.uniandes.miswmobile.vinilosapp.ui.adapters;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.AlbumTrackItemBinding
import co.edu.uniandes.miswmobile.vinilosapp.models.Track

class AlbumTrackAdapter :
    RecyclerView.Adapter<AlbumTrackAdapter.AlbumTrackViewHolder>() {

    var tracks: List<Track> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onItemClick: ((Track) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumTrackAdapter.AlbumTrackViewHolder {
        val withDataBinding: AlbumTrackItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumTrackAdapter.AlbumTrackViewHolder.LAYOUT,
            parent,
            false
        )

        return AlbumTrackAdapter.AlbumTrackViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumTrackAdapter.AlbumTrackViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.track = tracks[position]

        }
        holder.viewDataBinding.root.setOnClickListener {
            onItemClick?.invoke(tracks[position])
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    class AlbumTrackViewHolder(val viewDataBinding: AlbumTrackItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_track_item
        }
    }

}


