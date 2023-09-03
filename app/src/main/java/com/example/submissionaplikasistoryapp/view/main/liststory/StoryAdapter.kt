package com.example.submissionaplikasistoryapp.view.main.liststory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.submissionaplikasistoryapp.data.database.ListStoryItem
import com.example.submissionaplikasistoryapp.databinding.ItemStoryBinding

class StoryAdapter(
    private val listener: (
        listStoryItem: ListStoryItem,
        imageView: View,
        nameView: View,
        dateView: View,
        descView: View
    ) -> Unit
) : PagingDataAdapter<ListStoryItem, StoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(view)
    }


    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.view.root.setOnClickListener {
            if (item != null) {
                listener.invoke(
                    item,
                    holder.view.ivStoryImage,
                    holder.view.tvStoryName,
                    holder.view.tvStoryDate,
                    holder.view.tvStoryDesc
                )
            }
        }
        if (item != null) {
            holder.bind(item)
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}

class StoryViewHolder(val view: ItemStoryBinding) : RecyclerView.ViewHolder(view.root) {
    fun bind(item: ListStoryItem) {
        view.listStoryItem = item

        view.executePendingBindings()

    }
}


