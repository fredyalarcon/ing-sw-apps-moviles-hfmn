package co.edu.uniandes.miswmobile.vinilosapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.miswmobile.vinilosapp.R
import co.edu.uniandes.miswmobile.vinilosapp.databinding.PerformerItemBinding
import co.edu.uniandes.miswmobile.vinilosapp.models.Performer
import com.squareup.picasso.Picasso

class PerformerAdapter : RecyclerView.Adapter<PerformerAdapter.PerformersViewHolder>() {

    var performers : List<Performer> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
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
            val currentArtist = performers[position]
            it.performer = currentArtist
            Picasso.get().load(currentArtist.image).into(holder.imageView)
        }
    }

    override fun getItemCount(): Int {
        return performers.size
    }

    class PerformersViewHolder(val viewDataBinding: PerformerItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.`performer_item`
        }
    }
}