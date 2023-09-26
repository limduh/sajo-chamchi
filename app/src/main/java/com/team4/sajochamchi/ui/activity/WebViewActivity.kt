package com.team4.sajochamchi.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.team4.sajochamchi.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "WebViewActivity"
        const val TITLE = "TITLE"
        const val LINK = "LINK"
        fun newIntent(
            context: Context,
            title: String,
            link: String,
        ) = Intent(context, WebViewActivity::class.java).apply {
            putExtra(TITLE, title)
            putExtra(LINK, link)
        }
    }

    private lateinit var binding : ActivityWebViewBinding
    private val title by lazy { intent.getStringExtra(TITLE) }
    private val link by lazy { intent.getStringExtra(LINK) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() = with(binding) {
        webView.apply {
            settings.run {
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                setSupportMultipleWindows(true)
            }
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            requireNotNull(link)
            link?.let { _link ->
                loadUrl(_link)
            }
        }

        backArrowImageView.setOnClickListener {
            finish()
        }
        titleTextView.text = title
    }


    override fun onBackPressed() { // 뒤로가기 기능 구현
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            finish()
        }
    }
}