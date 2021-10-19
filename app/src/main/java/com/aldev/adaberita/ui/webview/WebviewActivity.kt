package com.aldev.adaberita.ui.webview

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.aldev.adaberita.R
import com.aldev.adaberita.databinding.ActivityWebViewBinding
import com.aldev.adaberita.model.response.ArticlesItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding
    private lateinit var newsUrl: String
    private lateinit var mainUrlNews: String
    private lateinit var articlesItem: ArticlesItem

    private lateinit var bookmarkIcon: MenuItem

    private val viewModel: WebviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        articlesItem = intent.getParcelableExtra("item")!!
        mainUrlNews = articlesItem.url ?: ""
        newsUrl = mainUrlNews

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_close_black)
            setDisplayHomeAsUpEnabled(true)
            title = articlesItem.title
        }

        binding.progressBar.max = 100
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                binding.progressBar.progress = newProgress
            }
        }
        binding.webView.webViewClient = MyWebViewClient()
        binding.webView.loadUrl(mainUrlNews)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView.canGoBack()) {
            binding.webView.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_webview_toolbar, menu)
        bookmarkIcon = menu.findItem(R.id.bookmark)

        viewModel.getBookmarkStatus(articlesItem.title)
        viewModel.bookmarkStatus.observe(this, { status ->
            if (status) {
                bookmarkIcon.icon =
                    ContextCompat.getDrawable(this, R.drawable.ic_bookmark_black_fill)
            } else {
                bookmarkIcon.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmark_black)
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> binding.webView.loadUrl(newsUrl)
            R.id.bookmark -> {
                viewModel.getBookmarkStatus(articlesItem.title)
                viewModel.bookmarkStatus.observe(this, { status ->
                    if (status) {
                        viewModel.deleteBookmark(articlesItem)
                        bookmarkIcon.icon =
                            ContextCompat.getDrawable(this, R.drawable.ic_bookmark_black)
                    } else {
                        viewModel.addBookmark(articlesItem)
                        bookmarkIcon.icon =
                            ContextCompat.getDrawable(this, R.drawable.ic_bookmark_black_fill)
                    }
                })
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private inner class MyWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (url != null) {
                newsUrl = url
            }
            val newsUrlCompare = Uri.parse(url).toString().substring(0, mainUrlNews.length)
            if (newsUrlCompare == mainUrlNews) {
                // This is my web site, so do not override; let my WebView load the page
                return false
            }
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            binding.progressBar.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.progressBar.visibility = View.GONE
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}