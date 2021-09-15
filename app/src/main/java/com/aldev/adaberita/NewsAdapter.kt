package com.aldev.adaberita

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.aldev.adaberita.databinding.ItemListNewsBinding
import com.aldev.adaberita.model.ArticlesItem
import com.bumptech.glide.Glide

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var newsList = mutableListOf<ArticlesItem>()

    fun setList(list: MutableList<ArticlesItem>) {
        newsList = list
        notifyDataSetChanged()
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

    inner class NewsViewHolder(val binding: ItemListNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: ArticlesItem) {
            Glide.with(binding.root)
                .load(item.urlToImage)
                .into(binding.ivNews)

            binding.tvNewsTitle.text = item.title
            binding.tvNewsSource.text = item.source?.name

            binding.root.setOnClickListener { view ->
                val value = HomeFragmentDirections.actionNavigationHomeToWebViewFragment(item.url)
                view.findNavController().navigate(value)
            }
        }
    }
}