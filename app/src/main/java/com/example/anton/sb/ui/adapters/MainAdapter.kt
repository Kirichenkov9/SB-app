package com.example.anton.sb.ui.adapters

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.extensions.updateDataList
import com.example.anton.sb.data.ResultAd
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread

class MainAdapter(private val ads: ArrayList<ResultAd>, private val itemClick: MainAdapter.OnItemClickListener) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { val view = LayoutInflater.from(parent.context).inflate(R.layout.content_main, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount() = ads.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindAd(ads[position])
    }

    class ViewHolder(itemView: View, private val itemClick: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        private val photoView = itemView.find<ImageView>(R.id.ad_photo)
        private val titleView = itemView.find<TextView>(R.id.ad_title)
        private val cityView = itemView.find<TextView>(R.id.ad_city)

        fun bindAd(ad: ResultAd) {
            with(ad) {
                Picasso
                    .with(itemView.context)
                    .load(ad_images?.get(0))
                    .placeholder(R.drawable.ic_image_ad)
                    .error(R.drawable.ic_image_ad)
                    .fit()
                    .centerCrop()
                    .into(photoView)
                titleView.text = title
                cityView.text = city
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }

    interface OnItemClickListener {
        operator fun invoke(ad: ResultAd)
    }

    class OnScrollListener(
        val layoutManager: LinearLayoutManager,
        val adapter: RecyclerView.Adapter<MainAdapter.ViewHolder>,
        val dataList: ArrayList<ResultAd>
    ) : RecyclerView.OnScrollListener() {
        var previousTotal = 0
        var loading = true
        val visibleThreshold = 5
        var firstVisibleItem = 0
        var visibleItemCount = 0
        var totalItemCount = 0

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            visibleItemCount = recyclerView.childCount
            totalItemCount = layoutManager.itemCount
            firstVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
            }

            if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                val initialSize = dataList.size
                doAsync {
                    updateDataList(dataList)
                    uiThread {
                        val updatedSize = dataList.size

                        recyclerView.post { adapter.notifyItemRangeInserted(initialSize, updatedSize) }
                    }
                }
                loading = true
            }
        }
    }
}
