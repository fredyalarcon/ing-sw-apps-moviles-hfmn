package co.edu.uniandes.miswmobile.vinilosapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.ArtistItemBinding
import co.edu.uniandes.miswmobile.vinilosapp.models.Musician
import com.squareup.picasso.Picasso

class MusiciansAdapter : RecyclerView.Adapter<MusiciansAdapter.MusiciansViewHolder>() {

    var musicians : List<Musician> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusiciansViewHolder {
        val withDataBinding: ArtistItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            MusiciansViewHolder.LAYOUT,
            parent,
            false
        )
        return MusiciansViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: MusiciansViewHolder, position: Int) {
        holder.viewDataBinding.also {
            val currentArtist = musicians[position]
            it.artist = currentArtist
            Picasso.get().load(currentArtist.image).into(holder.imageView)
        }
    }

    override fun getItemCount(): Int {
        return musicians.size
    }

    class MusiciansViewHolder(val viewDataBinding: ArtistItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.artist_item
        }
    }
}