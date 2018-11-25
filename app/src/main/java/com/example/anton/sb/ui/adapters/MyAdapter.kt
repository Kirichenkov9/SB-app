package com.example.anton.sb.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ResponseClasses.ResultAd
import org.jetbrains.anko.find


class MyAdapter(private val ads: ArrayList<ResultAd>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_main, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = ads.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = ads[position].title
        holder.city.text = ads[position].city
        holder.price.text = ads[position].price.toString()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photo = itemView.find<ImageView>(R.id.ad_photo)
        val title= itemView.find<TextView>(R.id.ad_title)
        val city = itemView.find<TextView>(R.id.ad_city)
        val price = itemView.find<TextView>(R.id.ad_price)
    }
}