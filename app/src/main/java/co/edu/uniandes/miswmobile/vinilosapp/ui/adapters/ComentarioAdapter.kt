package co.edu.uniandes.miswmobile.vinilosapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.AlbumItemBinding
import co.edu.uniandes.miswmobile.vinilosapp.databinding.ComentarioItemBinding
import co.edu.uniandes.miswmobile.vinilosapp.models.Album
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


class ComentarioAdapter :
    RecyclerView.Adapter<ComentarioAdapter.ComentarioViewHolder>() {

    var albums: List<Album> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onItemClick: ((Album) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioViewHolder {
        val withDataBinding: ComentarioItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ComentarioViewHolder.LAYOUT,
            parent,
            false
        )

        return ComentarioViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ComentarioViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album = albums[position]

        }
        holder.bind(albums[position])
        holder.viewDataBinding.root.setOnClickListener {
            onItemClick?.invoke(albums[position])
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    class ComentarioViewHolder(val viewDataBinding: ComentarioItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }
        fun bind(album: Album) {
            Glide.with(itemView)
                .load(album.cover.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.progress_animation)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_broken_image))
                .into(viewDataBinding.imageView)
        }
    }
}