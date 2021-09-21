package com.aldev.adaberita.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aldev.adaberita.data.source.remote.network.RetrofitServer
import com.aldev.adaberita.data.source.remote.response.ArticlesItem
import com.aldev.adaberita.databinding.FragmentHomeBinding
import com.aldev.adaberita.utils.Status
import com.aldev.adaberita.utils.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NewsAdapter
    private val service = RetrofitServer.getService()
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NewsAdapter()
        binding.rvHome.adapter = adapter
        binding.rvHome.setHasFixedSize(true)

        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        //getNewsData()

        viewModel.data.observe(viewLifecycleOwner, Observer { resource ->
            toggleLoading(resource.status)
            resource.data?.let { showData(it) }
        })

        adapter.setOnClickListener(object : NewsAdapter.OnItemClickListener {
            override fun onClick(newsUrl: String) {
                val intent = Intent(activity, WebViewActivity::class.java)
                intent.putExtra("url", newsUrl)
                activity?.startActivity(intent)
            }
        })
    }

    private fun showData(data: List<ArticlesItem>) {
        adapter.setList(data)
        binding.rvHome.visibility = View.VISIBLE
    }

    private fun toggleLoading(status: Status) {
        when (status) {
            Status.LOADING -> binding.progressCircular.visibility = View.VISIBLE
            else -> binding.progressCircular.visibility = View.GONE
        }
    }

    private fun getNewsData() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getHeadlinesNews()

            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data?.status == "ok") {
                            adapter.setList(data.articles)
                        }
                    }
                } catch (e: HttpException) {
                    Toast.makeText(
                        context,
                        "Exception ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Throwable) {
                    Toast.makeText(
                        context,
                        "Something else went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}