package com.aldev.adaberita.ui.bookmarks

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.aldev.adaberita.ui.webview.WebviewActivity
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

        viewModel.data.observe(viewLifecycleOwner, { list ->
            Log.d("TAG", "list size: ${list.size}")
            recyclerViewAdapter.setBookmarkList(list)
        })

        recyclerViewAdapter.setOnClickListener(object :
            NewsRecyclerViewAdapter.OnItemClickListener {
            override fun onClick(item: BookmarkNewsEntity) {
                val intent = Intent(activity, WebviewActivity::class.java)
                intent.putExtra("item", item)
                activity?.startActivity(intent)
            }
        })

        binding.swipeRefresh.setOnRefreshListener { }
    }
}