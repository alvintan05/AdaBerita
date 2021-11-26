package com.aldev.adaberita.adapter

import com.aldev.adaberita.R
import com.aldev.adaberita.base.BaseRecyclerViewAdapter
import com.aldev.adaberita.databinding.ItemListNewsBinding
import com.aldev.adaberita.model.entity.BookmarkNewsEntity
import com.aldev.adaberita.model.response.ArticlesItem

class NewsRecyclerViewAdapter : BaseRecyclerViewAdapter<BookmarkNewsEntity, ItemListNewsBinding>() {

//    private var newsList = listOf<ArticlesItem>()
//    private var bookmarkList = arrayListOf<BookmarkNewsEntity>()

    //private lateinit var listener: OnItemClickListener

//    fun setBookmarkList(list: List<BookmarkNewsEntity>) {
//        bookmarkList.clear()
//        bookmarkList.addAll(list)
//        notifyDataSetChanged()
//    }

//    fun setOnClickListener(listener: OnItemClickListener) {
//        this.listener = listener
//    }
//
//    interface OnItemClickListener {
//        fun onClick(item: BookmarkNewsEntity)
//    }

    override fun getLayoutResourceId(): Int = R.layout.item_list_news

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ItemListNewsBinding>,
        position: Int
    ) {
        holder.binding.news = items[position]
        holder.binding.root.setOnClickListener {
            listener?.invoke(it, items[position], position)
        }
    }

//    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
//        val item = bookmarkList[position]
//        holder.binding.news = item
//        holder.binding.root.setOnClickListener {
//            listener.onClick(item)
//        }
//    }


//    inner class NewsViewHolder(val binding: ItemListNewsBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bindItem(item: BookmarkNewsEntity) {
//            Glide.with(binding.root)
//                .load(item.urlToImage)
//                .into(binding.ivNews)
//
//            binding.tvNewsTitle.text = item.title
//            binding.tvNewsSource.text = item.sourceName
//        }
//    }
}