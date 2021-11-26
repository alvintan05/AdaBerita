package com.aldev.adaberita.ui.bookmarks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.aldev.adaberita.base.BaseFragment
import com.aldev.adaberita.R
import com.aldev.adaberita.adapter.NewsRecyclerViewAdapter
import com.aldev.adaberita.databinding.FragmentBookmarksBinding
import com.aldev.adaberita.model.entity.BookmarkNewsEntity
import com.aldev.adaberita.ui.webview.WebviewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarksFragment : BaseFragment<FragmentBookmarksBinding>() {

    private val viewModel: BookmarksViewModel by viewModels()
    private val recyclerViewAdapter by lazy {
        NewsRecyclerViewAdapter()
    }

    override fun getFragmentView(): Int = R.layout.fragment_bookmarks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvBookmarks.adapter = recyclerViewAdapter
        binding.rvBookmarks.setHasFixedSize(true)

        viewModel.data.observe(viewLifecycleOwner, { list ->
            Log.d("TAG", "list size: ${list.size}")
            recyclerViewAdapter.setItemList(list)
        })

        recyclerViewAdapter.listener = { view, item, position ->
            val intent = Intent(activity, WebviewActivity::class.java)
            intent.putExtra("item", item)
            activity?.startActivity(intent)
        }
    }
}