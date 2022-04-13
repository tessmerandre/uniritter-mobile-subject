package com.tessmerandre.listsndetails.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tessmerandre.listsndetails.data.ListItem
import com.tessmerandre.listsndetails.databinding.ItemListBinding
import java.util.*

class ListAdapter(
    private val onItemClick: (ListItem) -> Unit
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var items = listOf<ListItem>()

    fun setItems(items: List<ListItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ListItem) {
            binding.tvTitle.text = item.getItemTitle().replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }

            binding.tvDescription.isVisible = item.getItemDescription() != null
            binding.tvDescription.text = item.getItemDescription()

            binding.ivPhoto.isVisible = item.getPhotoUrl() != null
            binding.ivPhoto.load(item.getPhotoUrl())

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }

    }

}