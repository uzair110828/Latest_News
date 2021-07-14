package com.example.latestnews.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.latestnews.R
import com.squareup.picasso.Picasso

class Detailed : AppCompatActivity() {
    private lateinit var tvDate:TextView
    private lateinit var tvSource:TextView
    private lateinit var tvTitle:TextView
    private lateinit var image:ImageView
    private lateinit var desc:TextView
    private lateinit var webView:WebView
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)
         tvTitle = findViewById<TextView>(R.id.tvTitle)
         tvDate = findViewById<TextView>(R.id.tvDate)
        tvSource =findViewById<TextView>(R.id.tvSource)
         image = findViewById<ImageView>(R.id.image)
        webView = findViewById(R.id.webView)
        desc = findViewById(R.id.tvDesc)
        progressBar= findViewById(R.id.webViewloader)

        progressBar.visibility = View.VISIBLE

        val title = intent.getStringExtra("title")
        val source = intent.getStringExtra("source")
        val time = intent.getStringExtra("time")
        val imageUrl = intent.getStringExtra("imageUrl")
        val url = intent.getStringExtra("url")
        val descip = intent.getStringExtra("desc")

        tvTitle.setText(title)
        tvDate.setText(time)
        tvSource.setText(source)
        desc.setText(descip)

        Picasso.get().load(imageUrl).into(image)

        webView.settings.domStorageEnabled=true
        webView.settings.javaScriptEnabled=true
        webView.scrollBarStyle=View.SCROLLBARS_INSIDE_OVERLAY
        webView.webViewClient= WebViewClient()
        webView.loadUrl(url)

        if (webView.isShown){
            progressBar.visibility = View.INVISIBLE
        }












    }
}