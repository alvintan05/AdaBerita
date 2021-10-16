package com.aldev.adaberita.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aldev.adaberita.databinding.ItemListNewsBinding
import com.aldev.adaberita.model.response.ArticlesItem
import com.bumptech.glide.Glide

class NewsPagingAdapter : PagingDataAdapter<ArticlesItem, NewsPagingAdapter.ViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemListNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindItem(it) }

    }

    inner class ViewHolder(private val binding: ItemListNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: ArticlesItem) {
            Glide.with(binding.root)
                .load(item.urlToImage)
                .into(binding.ivNews)

            binding.tvNewsTitle.text = item.title
            binding.tvNewsSource.text = item.source?.name
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean =
                oldItem.title == newItem.title


            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean =
                oldItem == newItem

        }
    }
}
