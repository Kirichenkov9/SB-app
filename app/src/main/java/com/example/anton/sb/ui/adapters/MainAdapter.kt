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


class MainAdapter(private val ads: ArrayList<ResultAd>, private val itemClick: MainAdapter.OnItemClickListener) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_main, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount() = ads.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindAd(ads[position])
    }

    class ViewHolder(itemView: View, private val itemClick: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val photoView = itemView.find<ImageView>(R.id.ad_photo)
        val titleView = itemView.find<TextView>(R.id.ad_title)
        val cityView = itemView.find<TextView>(R.id.ad_city)
        val priceView = itemView.find<TextView>(R.id.ad_price)

        fun bindAd(ad: ResultAd) {
            with(ad) {
                titleView.text = title
                cityView.text = city
                priceView.text = price.toString()
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }

    interface OnItemClickListener {
        operator fun invoke(ad: ResultAd)
    }
}
