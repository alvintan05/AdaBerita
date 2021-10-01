package com.aldev.adaberita.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aldev.adaberita.adapter.NewsRecyclerViewAdapter
import com.aldev.adaberita.data.source.local.entity.BookmarkNewsEntity
import com.aldev.adaberita.data.source.remote.response.ArticlesItem
import com.aldev.adaberita.databinding.FragmentHomeBinding
import com.aldev.adaberita.ui.WebViewActivity
import com.aldev.adaberita.utils.Status
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerViewAdapter: NewsRecyclerViewAdapter
    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

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