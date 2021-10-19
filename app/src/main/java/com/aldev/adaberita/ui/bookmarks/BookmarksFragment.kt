package com.aldev.adaberita.ui.bookmarks

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aldev.adaberita.R
import com.aldev.adaberita.adapter.NewsRecyclerViewAdapter
import com.aldev.adaberita.model.entity.BookmarkNewsEntity
import com.aldev.adaberita.model.response.ArticlesItem
import com.aldev.adaberita.databinding.FragmentBookmarksBinding
import com.aldev.adaberita.ui.WebViewActivity
import com.aldev.adaberita.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarksFragment : Fragment() {

    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerViewAdapter: NewsRecyclerViewAdapter
    private val viewModel: BookmarksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmarks, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewAdapter = NewsRecyclerViewAdapter()
        binding.rvBookmarks.adapter = recyclerViewAdapter
        binding.rvBookmarks.setHasFixedSize(true)
        recyclerViewAdapter.setBookmarkStatus(true)

        viewModel.data.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Loading -> binding.swipeRefresh.isRefreshing = true
                is Resource.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    resource.data?.let { showData(it) }
                }
                is Resource.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    resource.error?.let { showError(it) }
                }
                is Resource.Empty -> {

                }
            }
        })

        recyclerViewAdapter.setOnClickListener(object :
            NewsRecyclerViewAdapter.OnItemClickListener {
            override fun onClick(item: ArticlesItem) {

            }

            override fun onClickFromBookmarks(item: BookmarkNewsEntity) {
                val intent = Intent(activity, WebViewActivity::class.java)
                intent.putExtra("id", item.id)
                intent.putExtra("url", item.url)
                intent.putExtra("newsTitle", item.title)
                activity?.startActivity(intent)
            }
        })

        binding.swipeRefresh.setOnRefreshListener { }
    }

    private fun showData(data: List<BookmarkNewsEntity>) {
        recyclerViewAdapter.setBookmarkList(data)
        binding.rvBookmarks.visibility = View.VISIBLE
        binding.tvErrorMessage.visibility = View.GONE
        binding.ivWarning.visibility = View.GONE
    }

    private fun showError(message: String) {
        binding.tvErrorMessage.text = message
        binding.tvErrorMessage.visibility = View.VISIBLE
        binding.ivWarning.visibility = View.VISIBLE
        binding.rvBookmarks.visibility = View.GONE
    }
}