package com.aldev.adaberita.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aldev.adaberita.data.source.local.entity.BookmarkNewsEntity
import com.aldev.adaberita.data.source.remote.response.ArticlesItem
import com.aldev.adaberita.databinding.ItemListNewsBinding
import com.bumptech.glide.Glide

class NewsRecyclerViewAdapter : RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder>() {

    private var newsList = listOf<ArticlesItem>()
    private var bookmarkList = listOf<BookmarkNewsEntity>()
    private lateinit var listener: OnItemClickListener
    private var isBookmarked = false

    fun setList(list: List<ArticlesItem>) {
        newsList = list
        notifyDataSetChanged()
    }

    fun setBookmarkList(list: List<BookmarkNewsEntity>) {
        bookmarkList = list
        notifyDataSetChanged()
    }

    fun setBookmarkStatus(status: Boolean) {
        isBookmarked = status
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            ItemListNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindItem(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size

    inner class NewsViewHolder(private val binding: ItemListNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: ArticlesItem) {
            Glide.with(binding.root)
                .load(item.urlToImage)
                .into(binding.ivNews)

            binding.tvNewsTitle.text = item.title
            binding.tvNewsSource.text = item.source?.name

            binding.root.setOnClickListener { view ->
                if (isBookmarked) {
                    listener.onClickFromBookmarks(
                        BookmarkNewsEntity(
                            publishedAt = item.publishedAt,
                            author = item.author,
                            urlToImage = item.urlToImage,
                            description = item.description,
                            sourceName = item.source?.name,
                            sourceId = item.source?.id,
                            title = item.title,
                            url = item.url,
                            content = item.content
                        )
                    )
                } else {
                    listener.onClick(item)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(item: ArticlesItem)
        fun onClickFromBookmarks(item: BookmarkNewsEntity)
    }
}