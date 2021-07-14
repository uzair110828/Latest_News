package com.example.latestnews.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.latestnews.R
import com.example.latestnews.activity.Detailed
import com.example.latestnews.model.Articles
import com.squareup.picasso.Picasso
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*

class Adapter(val context:Context, val articles:List<Articles>): RecyclerView.Adapter<Adapter.Viewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val a = articles.get(position)
        holder.tvTitle.setText(a.title)
        holder.tvSource.setText(a.source.name)
        holder.tvDate.setText(dateTime(a.publishedAt))

       Picasso.get().load(a.urlToImage).into(holder.image)

        holder.cardView.setOnClickListener {
            val intent = Intent(context.applicationContext , Detailed::class.java)
            intent.putExtra("title",a.title)
            intent.putExtra("source",a.source.name)
            intent.putExtra("desc",a.description)
            intent.putExtra("time",dateTime(a.publishedAt))
            intent.putExtra("imageUrl",a.urlToImage)
            intent.putExtra("url",a.url)
            context.startActivity(intent)
        }

    }

    override fun getItemCount() = articles.size

    public class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        val tvSource = itemView.findViewById<TextView>(R.id.tvSource)
        val image = itemView.findViewById<ImageView>(R.id.image)
        val cardView = itemView.findViewById<CardView>(R.id.cardView)
    }
    fun dateTime(t:String): String {
        var prettyTime = PrettyTime(Locale(getCountry()))
        var time:String? = null
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:",Locale.ENGLISH)
        var date:Date = Date()
        date = simpleDateFormat.parse(t)
        time = prettyTime.format(date)
        return time

    }
    fun getCountry():String{
        val local= Locale.getDefault()
        val country = local.country
        return country.toLowerCase()
    }
}