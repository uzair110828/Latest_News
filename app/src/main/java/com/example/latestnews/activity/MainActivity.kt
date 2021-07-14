package com.example.latestnews.activity

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.latestnews.Adapter.Adapter
import com.example.latestnews.Api.ApiClient
import com.example.latestnews.R
import com.example.latestnews.model.Articles
import com.example.latestnews.model.headlines
import com.example.wallpapers.Check_Network.network_receiver
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() , network_receiver.ConnectivityReceiverListener{
    private lateinit var recyclerView:RecyclerView
    private lateinit var editText: EditText
    private var snackBar: Snackbar? = null
    private lateinit var btnSearch: ImageView
    private lateinit var swipeRefreshlayout: SwipeRefreshLayout
    val API_KEY:String = "6c6c396762304f1ab936f3a0951024d2"
    private lateinit var adapter: Adapter
    var article = ArrayList<Articles>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        btnSearch = findViewById(R.id.button)
        editText = findViewById(R.id.edittext)
        swipeRefreshlayout= findViewById(R.id.swiperefresh)
        registerReceiver(network_receiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        recyclerView.layoutManager = LinearLayoutManager(this)

        val country = getCountry()
        swipeRefreshlayout.setOnRefreshListener {
            retrieveJson("",country,API_KEY)
        }
        retrieveJson("",country,API_KEY)

        btnSearch.setOnClickListener {
            val text = editText.text.toString()
            if (!text.equals(""))
            {
                swipeRefreshlayout.setOnRefreshListener {
                    retrieveJson(text,country,API_KEY)
                }
                retrieveJson(text,country,API_KEY)

            }else{
                swipeRefreshlayout.setOnRefreshListener {
                    retrieveJson("",country,API_KEY)
                }
                retrieveJson("",country,API_KEY)

            }
        }
    }

    fun retrieveJson(query:String, country:String , apiKey: String){

        swipeRefreshlayout.isRefreshing = true
         lateinit var call:Call<headlines>
        if (!editText.text.toString().equals("")){
             call= ApiClient.create().getSpecific(query,apiKey)
        }else{
            call= ApiClient.create().getHeadlines(country,apiKey)
        }
        call.enqueue(object : Callback<headlines> {
            override fun onResponse(call: Call<headlines>, response: Response<headlines>) {
                swipeRefreshlayout.isRefreshing = false
                if (response.isSuccessful() && response.body()?.articles !== null) {
                    article.clear()
                    article = response.body()!!.articles as ArrayList<Articles>
                    adapter = Adapter(this@MainActivity, article)
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<headlines>, t: Throwable) {
                swipeRefreshlayout.isRefreshing = false
                Toast.makeText(this@MainActivity, "${t.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
                Log.i("tag", "onFailure: ${t.localizedMessage}")
            }
        })
    }

    fun getCountry():String{
        val local= Locale.getDefault()
        val country = local.country
        return country.toLowerCase()
    }


    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    override fun onResume() {
        super.onResume()
        network_receiver.connectivityReceiverListener = this
    }
    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            snackBar = Snackbar.make(findViewById(R.id.linearL), "You are offline", Snackbar.LENGTH_LONG) //Assume "rootLayout" as the root layout of every activity.
            snackBar?.duration = BaseTransientBottomBar.LENGTH_INDEFINITE
            snackBar?.show()
        } else {
            snackBar?.dismiss()
        }
    }


}