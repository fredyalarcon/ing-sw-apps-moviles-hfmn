package co.edu.uniandes.miswmobile.vinilosapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.AlbumItemBinding
import co.edu.uniandes.miswmobile.vinilosapp.models.Album
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    private lateinit var progressBar: ProgressBar

    var albums :List<Album> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumViewHolder.LAYOUT,
            parent,
            false)

        val albumViewHolder = AlbumViewHolder(withDataBinding)

        return AlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]

        holder.viewDataBinding.also {
            it.album = album
            // Hacer una copia local inmutable del álbum
            val currentAlbum = it.album

            // Obtener el contexto desde el ViewGroup parent del holder
            val context = holder.itemView.context

            // Utilizar la copia local para cargar la imagen
            // aserción no nula utilizando el operador de aserción no nula (!!)
            holder.progressBarAlbumItem?.let { progressBar ->
                Picasso.get()
                    .load(currentAlbum!!.cover)
                    .error(R.drawable.progress_animation)
                    .placeholder(R.drawable.progress_animation)
                    .into(holder.imageView, object : Callback {
                        override fun onSuccess() {
                            progressBar.visibility = View.GONE
                            Log.d("Picasso", "Imagen cargada con éxito")
                        }

                        override fun onError(e: Exception?) {
                            Log.e("Picasso", "Error al cargar la imagen", e)
                        }
                    })
            }
        }
        holder.viewDataBinding.root.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    class AlbumViewHolder(val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val progressBarAlbumItem: ProgressBar = itemView.findViewById(R.id.progressBarAlbumItem)
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }
    }
}
