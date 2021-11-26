package com.aldev.adaberita.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.aldev.adaberita.base.BaseFragment
import com.aldev.adaberita.R
import com.aldev.adaberita.adapter.NewsPagingAdapter
import com.aldev.adaberita.databinding.FragmentHomeBinding
import com.aldev.adaberita.model.entity.BookmarkNewsEntity
import com.aldev.adaberita.ui.webview.WebviewActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var recyclerViewAdapter: NewsPagingAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun getFragmentView(): Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewAdapter = NewsPagingAdapter()
        binding.rvHome.setHasFixedSize(true)
        binding.rvHome.adapter = recyclerViewAdapter

        viewModel.data.observe(viewLifecycleOwner, { resource ->
            recyclerViewAdapter.submitData(viewLifecycleOwner.lifecycle, resource)
        })

        lifecycleScope.launch {
            recyclerViewAdapter.loadStateFlow.collectLatest {
                binding.swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                binding.rvHome.isVisible = it.refresh !is LoadState.Error
                binding.ivWarning.isVisible = it.refresh is LoadState.Error
            }
        }

        recyclerViewAdapter.setOnClickListener(object :
            NewsPagingAdapter.OnItemClickListener {
            override fun onClick(item: BookmarkNewsEntity) {
                val intent = Intent(activity, WebviewActivity::class.java)
                intent.putExtra("item", item)
                activity?.startActivity(intent)
            }
        })

        binding.swipeRefresh.setOnRefreshListener { recyclerViewAdapter.refresh() }
    }
}