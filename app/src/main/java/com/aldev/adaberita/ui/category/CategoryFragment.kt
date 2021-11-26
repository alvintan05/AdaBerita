package com.aldev.adaberita.ui.category

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.aldev.adaberita.base.BaseFragment
import com.aldev.adaberita.R
import com.aldev.adaberita.adapter.NewsPagingAdapter
import com.aldev.adaberita.databinding.FragmentCategoryBinding
import com.aldev.adaberita.model.entity.BookmarkNewsEntity
import com.aldev.adaberita.ui.webview.WebviewActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(), AdapterView.OnItemSelectedListener {

    private lateinit var recyclerViewAdapter: NewsPagingAdapter
    private val viewModel: CategoryViewModel by viewModels()
    private var headlineId: String = " "

    override fun getFragmentView(): Int = R.layout.fragment_category

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSpinner()

        recyclerViewAdapter = NewsPagingAdapter()
        binding.rvCategory.apply {
            adapter = recyclerViewAdapter
            setHasFixedSize(true)
        }

        lifecycleScope.launch {
            recyclerViewAdapter.loadStateFlow.collectLatest {
                binding.swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                binding.rvCategory.isVisible = it.refresh !is LoadState.Error
            }
        }

        recyclerViewAdapter.setOnClickListener(object :
            NewsPagingAdapter.OnItemClickListener {
            override fun onClick(item: BookmarkNewsEntity) {
                val intent = Intent(activity, WebviewActivity::class.java)
                intent.putExtra("url", item.url)
                intent.putExtra("newsTitle", item.title)
                activity?.startActivity(intent)
            }
        })


        binding.swipeRefresh.setOnRefreshListener { recyclerViewAdapter.refresh() }
    }

    private fun setUpSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.headline_category_title,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCategory.adapter = adapter
        }

        binding.spinnerCategory.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val headlineIdList = resources.getStringArray(R.array.headline_category_id)

        headlineId = headlineIdList[position]

        if (headlineId != "") {
            viewModel.getData(headlineId)
            viewModel.data.observe(viewLifecycleOwner, { resource ->
                if (resource != null) {
                    recyclerViewAdapter.submitData(viewLifecycleOwner.lifecycle, resource)
                }
            })
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}