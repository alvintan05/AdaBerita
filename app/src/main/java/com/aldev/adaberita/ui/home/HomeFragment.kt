package com.aldev.adaberita.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.aldev.adaberita.R
import com.aldev.adaberita.adapter.NewsPagingAdapter
import com.aldev.adaberita.databinding.FragmentHomeBinding
import com.aldev.adaberita.model.response.ArticlesItem
import com.aldev.adaberita.ui.WebViewActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerViewAdapter: NewsPagingAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        _binding!!.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

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
            override fun onClick(item: ArticlesItem) {
                val intent = Intent(activity, WebViewActivity::class.java)
                intent.putExtra("url", item.url)
                intent.putExtra("newsTitle", item.title)
                activity?.startActivity(intent)
            }
        })

        binding.swipeRefresh.setOnRefreshListener { recyclerViewAdapter.refresh() }
    }
}