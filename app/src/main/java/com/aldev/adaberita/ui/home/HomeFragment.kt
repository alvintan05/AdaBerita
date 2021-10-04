package com.aldev.adaberita.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.aldev.adaberita.adapter.NewsRecyclerViewAdapter
import com.aldev.adaberita.data.source.local.entity.BookmarkNewsEntity
import com.aldev.adaberita.data.source.remote.response.ArticlesItem
import com.aldev.adaberita.databinding.FragmentHomeBinding
import com.aldev.adaberita.ui.WebViewActivity
import com.aldev.adaberita.utils.Resource
import com.aldev.adaberita.utils.Status
import com.aldev.adaberita.utils.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerViewAdapter: NewsRecyclerViewAdapter
//    private lateinit var viewModel: HomeViewModel
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewAdapter = NewsRecyclerViewAdapter()
        binding.rvHome.adapter = recyclerViewAdapter
        binding.rvHome.setHasFixedSize(true)

//        val factory = ViewModelFactory.getInstance(requireActivity())
//        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        viewModel.data.observe(viewLifecycleOwner, { resource ->
            when (resource.status) {
                Status.LOADING -> binding.swipeRefresh.isRefreshing = true
                Status.SUCCESS -> {
                    binding.swipeRefresh.isRefreshing = false
                    resource.data?.let { showData(it) }
                }
                Status.ERROR -> {
                    binding.swipeRefresh.isRefreshing = false
                    resource.message?.let { showError(it) }
                }
            }
        })

        recyclerViewAdapter.setOnClickListener(object :
            NewsRecyclerViewAdapter.OnItemClickListener {
            override fun onClick(item: ArticlesItem) {
                val intent = Intent(activity, WebViewActivity::class.java)
                intent.putExtra("url", item.url)
                intent.putExtra("newsTitle", item.title)
                activity?.startActivity(intent)
            }

            override fun onClickFromBookmarks(item: BookmarkNewsEntity) {
                TODO("Not yet implemented")
            }
        })

        binding.swipeRefresh.setOnRefreshListener { viewModel.getData() }
    }

    private fun showData(data: List<ArticlesItem>) {
        recyclerViewAdapter.setList(data)
        binding.rvHome.visibility = View.VISIBLE
        binding.tvErrorMessage.visibility = View.GONE
        binding.ivWarning.visibility = View.GONE
    }

    private fun showError(message: String) {
        binding.tvErrorMessage.text = message
        binding.tvErrorMessage.visibility = View.VISIBLE
        binding.ivWarning.visibility = View.VISIBLE
        binding.rvHome.visibility = View.GONE
    }
}