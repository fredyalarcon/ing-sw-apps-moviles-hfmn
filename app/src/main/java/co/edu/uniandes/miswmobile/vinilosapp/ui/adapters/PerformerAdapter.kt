package co.edu.uniandes.miswmobile.vinilosapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.PerformerItemBinding
import co.edu.uniandes.miswmobile.vinilosapp.models.Album
import co.edu.uniandes.miswmobile.vinilosapp.models.Performer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class PerformerAdapter : RecyclerView.Adapter<PerformerAdapter.PerformersViewHolder>() {

    var performers : List<Performer> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemClick: ((Performer) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformersViewHolder {
        val withDataBinding: PerformerItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            PerformersViewHolder.LAYOUT,
            parent,
            false
        )
        return PerformersViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: PerformersViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.performer = performers[position]
        }
        holder.bind(performers[position])
        holder.viewDataBinding.root.setOnClickListener {
            onItemClick?.invoke(performers[position])
        }
    }

    override fun getItemCount(): Int {
        return performers.size
    }

    class PerformersViewHolder(val viewDataBinding: PerformerItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.performer_item
        }

        fun bind(performer: Performer) {
            Glide.with(itemView)
                .load(performer.image.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.progress_animation)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_broken_image))
                .into(viewDataBinding.imageView)
        }
    }
}