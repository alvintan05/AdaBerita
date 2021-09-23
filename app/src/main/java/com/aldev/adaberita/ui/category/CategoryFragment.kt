package com.aldev.adaberita.ui.category

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aldev.adaberita.R
import com.aldev.adaberita.adapter.NewsRecyclerViewAdapter
import com.aldev.adaberita.data.source.remote.network.RetrofitServer
import com.aldev.adaberita.data.source.remote.response.ArticlesItem
import com.aldev.adaberita.databinding.FragmentCategoryBinding
import com.aldev.adaberita.ui.WebViewActivity
import com.aldev.adaberita.utils.Status
import com.aldev.adaberita.utils.ViewModelFactory

class CategoryFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerViewAdapter: NewsRecyclerViewAdapter
    private lateinit var viewModel: CategoryViewModel
    private var headlineId: String = " "

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

        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[CategoryViewModel::class.java]

        viewModel.data.observe(viewLifecycleOwner, { resource ->
            toggleLoading(resource.status)
            resource.data?.let { showData(it) }
        })

        recyclerViewAdapter.setOnClickListener(object :
            NewsRecyclerViewAdapter.OnItemClickListener {
            override fun onClick(newsUrl: String) {
                val intent = Intent(activity, WebViewActivity::class.java)
                intent.putExtra("url", newsUrl)
                activity?.startActivity(intent)
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

    private fun toggleLoading(status: Status) {
        when (status) {
            Status.LOADING -> binding.swipeRefresh.isRefreshing = true
            else -> binding.swipeRefresh.isRefreshing = false
        }
    }
}