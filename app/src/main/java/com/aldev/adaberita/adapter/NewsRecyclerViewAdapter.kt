package com.aldev.adaberita.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aldev.adaberita.R
import com.aldev.adaberita.model.entity.BookmarkNewsEntity
import com.aldev.adaberita.model.response.ArticlesItem
import com.aldev.adaberita.databinding.ItemListNewsBinding
import com.bumptech.glide.Glide

class NewsRecyclerViewAdapter : RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder>() {

    private var newsList = listOf<ArticlesItem>()
    private var bookmarkList = arrayListOf<BookmarkNewsEntity>()
    private lateinit var listener: OnItemClickListener
    private var isBookmarked = false

    fun setList(list: List<ArticlesItem>) {
        newsList = list
        notifyDataSetChanged()
    }

    fun setBookmarkList(list: List<BookmarkNewsEntity>) {
        bookmarkList.clear()
        bookmarkList.addAll(list)
        notifyDataSetChanged()
    }

    fun setBookmarkStatus(status: Boolean) {
        isBookmarked = status
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_list_news,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = bookmarkList[position]
        holder.binding.news = item
        holder.binding.root.setOnClickListener {
            listener.onClick(item)
        }
    }

    override fun getItemCount(): Int = bookmarkList.size

    inner class NewsViewHolder(val binding: ItemListNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: BookmarkNewsEntity) {
            Glide.with(binding.root)
                .load(item.urlToImage)
                .into(binding.ivNews)

            binding.tvNewsTitle.text = item.title
            binding.tvNewsSource.text = item.sourceName

//            binding.root.setOnClickListener { view ->
//                if (isBookmarked) {
//                    listener.onClickFromBookmarks(
//                        BookmarkNewsEntity(
//                            publishedAt = item.publishedAt,
//                            author = item.author,
//                            urlToImage = item.urlToImage,
//                            description = item.description,
//                            sourceName = item.source?.name,
//                            sourceId = item.source?.id,
//                            title = item.title,
//                            url = item.url,
//                            content = item.content
//                        )
//                    )
//                } else {
//                    listener.onClick(item)
//                }
//            }
        }
    }

    interface OnItemClickListener {
        fun onClick(item: BookmarkNewsEntity)
    }
}