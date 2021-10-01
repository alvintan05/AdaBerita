package com.aldev.adaberita.ui.category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.aldev.adaberita.R
import com.aldev.adaberita.adapter.NewsRecyclerViewAdapter
import com.aldev.adaberita.data.source.local.entity.BookmarkNewsEntity
import com.aldev.adaberita.data.source.remote.response.ArticlesItem
import com.aldev.adaberita.databinding.FragmentCategoryBinding
import com.aldev.adaberita.ui.WebViewActivity
import com.aldev.adaberita.utils.Status
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CategoryFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerViewAdapter: NewsRecyclerViewAdapter

    @Inject
    lateinit var viewModel: CategoryViewModel
    private var headlineId: String = " "

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSpinner()

        recyclerViewAdapter = NewsRecyclerViewAdapter()
        binding.rvCategory.apply {
            adapter = recyclerViewAdapter
            setHasFixedSize(true)
        }

        viewModel.data.observe(viewLifecycleOwner, { resource ->
            when (resource.status) {
                Status.LOADING -> binding.swipeRefresh.isRefreshing = true
                Status.SUCCESS -> {
                    binding.swipeRefresh.isRefreshing = false
                    resource.data?.let { showData(it) }
                }
                Status.ERROR -> {
                    binding.swipeRefresh.isRefreshing = false
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

        binding.swipeRefresh.setOnRefreshListener { viewModel.getData(headlineId) }
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

        if (headlineId != "") viewModel.getData(headlineId)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun showData(data: List<ArticlesItem>) {
        recyclerViewAdapter.setList(data)
        binding.rvCategory.visibility = View.VISIBLE
    }
}