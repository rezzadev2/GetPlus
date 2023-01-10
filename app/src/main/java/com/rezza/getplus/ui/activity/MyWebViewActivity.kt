package com.rezza.getplus.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.balysv.materialripple.MaterialRippleLayout
import com.rezza.getplus.R
import com.rezza.getplus.ui.view.Loading

class MyWebViewActivity  : AppCompatActivity() {

    private lateinit var mrly_close : MaterialRippleLayout
    private lateinit var txvw_title : TextView
    private lateinit var wbvw_main : WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        mrly_close = findViewById(R.id.mrly_close)
        txvw_title = findViewById(R.id.txvw_title)
        wbvw_main  = findViewById(R.id.wbvw_main)

        wbvw_main.settings.loadsImagesAutomatically = true
        wbvw_main.settings.javaScriptEnabled = true
        wbvw_main.settings.domStorageEnabled = true
        wbvw_main.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        wbvw_main.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                Loading.cancelLoading()
            }
        }

        initListener()
        initData()
    }

    private fun initData(){
        txvw_title.text = intent.getStringExtra("title")
        Loading.showLoading(this, "Please wait...")
        val url = intent.getStringExtra("url") as String

        wbvw_main.loadUrl(url)




    }

    private fun initListener(){
        mrly_close.setOnClickListener { finish() }
    }
}