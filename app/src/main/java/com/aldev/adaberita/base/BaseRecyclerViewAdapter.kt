package com.aldev.adaberita.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T : Any, VB : ViewDataBinding>
    : RecyclerView.Adapter<BaseRecyclerViewAdapter.Companion.BaseViewHolder<VB>>() {

    var items = mutableListOf<T>()
    var listener : ((view: View, item: T, position: Int) -> Unit)? = null

    fun setItemList(items: List<T>) {
        this.items = items as MutableList<T>
        notifyDataSetChanged()
    }

    abstract fun getLayoutResourceId(): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> =
        BaseViewHolder<VB>(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                getLayoutResourceId(),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    companion object {
        class BaseViewHolder<VB : ViewDataBinding>(val binding: VB) :
            RecyclerView.ViewHolder(binding.root)
    }
}