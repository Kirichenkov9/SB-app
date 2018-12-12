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
import org.jetbrains.anko.image
import org.jetbrains.anko.uiThread

/**
 * RecyclerView Adapter for display ads
 *
 * @author Anton Kirichenkov
 */
class MainAdapter(private val ads: ArrayList<ResultAd>, private val itemClick: MainAdapter.OnItemClickListener) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    /**
     * @property ads
     * @property itemClick
     */

    /**
     * @suppress
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { val view = LayoutInflater.from(parent.context).inflate(R.layout.content_main, parent, false)
        return ViewHolder(view, itemClick)
    }

    /**
     * @suppress
     */
    override fun getItemCount() = ads.size

    /**
     * @suppress
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindAd(ads[position])
    }

    /**
     * Class for interaction with layout
     */
    class ViewHolder(itemView: View, private val itemClick: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

        /**
         * @property itemView
         * @property itemClick
         * @property photoView
         * @property titleView
         * @property cityView
         */

        /**
         * id of photo on layout
         */
        private val photoView = itemView.find<ImageView>(R.id.ad_photo)
        /**
         * id of title on layout
         */
        private val titleView = itemView.find<TextView>(R.id.ad_title)
        /**
         * id of city on layout
         */
        private val cityView = itemView.find<TextView>(R.id.ad_city)

        /**
         * Set information about ad in layout
         */
        fun bindAd(ad: ResultAd) {
            with(ad) {
                if (ad_images.isEmpty()) {
                    Picasso
                        .with(itemView.context)
                        .load(ad_images[0])
                        .placeholder(R.drawable.ic_image_ad)
                        .error(R.drawable.ic_image_ad)
                        .fit()
                        .centerCrop()
                        .into(photoView)
                }
                titleView.text = title
                cityView.text = city
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }

    /**
     * Interface for processing click on ad
     */
    interface OnItemClickListener {
        /**
         * @suppress
         */
        operator fun invoke(ad: ResultAd)
    }

    /**
     * Class for automatic update list of ads.
     *
     * @return  RecyclerView.OnScrollListener()
     */
    class OnScrollListener(
        val layoutManager: LinearLayoutManager,
        val adapter: RecyclerView.Adapter<MainAdapter.ViewHolder>,
        val dataList: ArrayList<ResultAd>
    ) : RecyclerView.OnScrollListener() {

        /**
         *  @property layoutManager
         *  @property adapter
         *  @property dataList
         *  @property previousTotal
         *  @property loading
         *  @property visibleThreshold
         *  @property firstVisibleItem
         *  @property visibleItemCount
         *  @property totalItemCount
         *
         */

        /**
         * Previous(before update) total item on recyclerView.
         */
        var previousTotal = 0

        /**
         * Flag for update list of ads
         */
        var loading = true

        /**
         * Threshold of visible item
         */
        val visibleThreshold = 10

        /**
         * Position of first visible item
         */
        var firstVisibleItem = 0

        /**
         * Count of visible item
         */
        var visibleItemCount = 0

        /**
         * Total iten count on list
         */
        var totalItemCount = 0

        /**
         * @suppress
         */
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
