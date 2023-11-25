package co.edu.uniandes.miswmobile.vinilosapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.AlbumComentarioItemFragmentBinding
import co.edu.uniandes.miswmobile.vinilosapp.models.Album
import co.edu.uniandes.miswmobile.vinilosapp.models.Comentario
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


class ComentarioAdapter :
    RecyclerView.Adapter<ComentarioAdapter.ComentarioViewHolder>() {

    var comentarios: List<Comentario> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onItemClick: ((Comentario) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComentarioAdapter.ComentarioViewHolder {
        val withDataBinding: AlbumComentarioItemFragmentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ComentarioAdapter.ComentarioViewHolder.LAYOUT,
            parent,
            false
        )

        return ComentarioAdapter.ComentarioViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ComentarioViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.comentario = comentarios[position]

        }
        holder.viewDataBinding.root.setOnClickListener {
            onItemClick?.invoke(comentarios[position])
        }
    }

    override fun getItemCount(): Int {
        return comentarios.size
    }

    class ComentarioViewHolder(val viewDataBinding: AlbumComentarioItemFragmentBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_comentario_item_fragment
        }
    }
}