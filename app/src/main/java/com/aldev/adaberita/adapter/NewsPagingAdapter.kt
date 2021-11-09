package com.aldev.adaberita.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aldev.adaberita.R
import com.aldev.adaberita.databinding.ItemListNewsBinding
import com.aldev.adaberita.model.entity.BookmarkNewsEntity
import com.aldev.adaberita.model.response.ArticlesItem
import com.bumptech.glide.Glide

class NewsPagingAdapter : PagingDataAdapter<BookmarkNewsEntity, NewsPagingAdapter.ViewHolder>(
    DIFF_CALLBACK
) {

    private lateinit var listener: OnItemClickListener

    fun setOnClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_list_news,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.news = item
        holder.binding.root.setOnClickListener {
            if (item != null) {
                listener.onClick(item)
            }
        }
    }

    inner class ViewHolder(val binding: ItemListNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

//        fun bindItem(item: ArticlesItem) {
//            Glide.with(binding.root)
//                .load(item.urlToImage)
//                .into(binding.ivNews)
//
//            binding.tvNewsTitle.text = item.title
//            binding.tvNewsSource.text = item.source?.name
//
//            binding.root.setOnClickListener {
//                listener.onClick(item)
//            }
//        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BookmarkNewsEntity>() {
            override fun areItemsTheSame(
                oldItem: BookmarkNewsEntity,
                newItem: BookmarkNewsEntity
            ): Boolean =
                oldItem.title == newItem.title


            override fun areContentsTheSame(
                oldItem: BookmarkNewsEntity,
                newItem: BookmarkNewsEntity
            ): Boolean =
                oldItem == newItem

        }
    }

    interface OnItemClickListener {
        fun onClick(item: BookmarkNewsEntity)
    }
}
