package com.example.anton.sb.ui.activities.adActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import com.example.anton.sb.R
import com.example.anton.sb.data.Extensions.updateSearchList
import com.example.anton.sb.data.ResponseClasses.ResultAd
import com.example.anton.sb.ui.adapters.SearchAdapter
import kotlinx.android.synthetic.main.activity_search.* // ktlint-disable no-wildcard-imports
import kotlinx.android.synthetic.main.app_bar_search.*  // ktlint-disable no-wildcard-imports
import org.jetbrains.anko.* // ktlint-disable no-wildcard-imports

class SearchActivity : AppCompatActivity() {

    private var request: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar_search)

        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val searchText = find<EditText>(R.id.search_edit_frame)
        val searchButton = find<ImageButton>(R.id.start_search)

        val list: ArrayList<ResultAd> = ArrayList()

        val recyclerView = find<RecyclerView>(R.id.search_list_ad)
        val layoutManager = LinearLayoutManager(this)

        var searchString: String = ""
        if (intent.hasExtra("request")) {
            searchString = intent.getStringExtra("request")
            searchText.setText(request)
            displayAds(list, searchText, recyclerView, layoutManager)
        }

        searchButton.setOnClickListener {
            if (searchText.text.isNullOrEmpty())
                toast("Вы ничего не вввели")
            else if (list.size == 0) {
                searchString = searchText.text.toString()
                displayAds(list, searchText, recyclerView, layoutManager)
                searchString = searchText.text.toString()
            } else if (searchText.text.toString() != searchString) {
                list.clear()
                displayAds(list, searchText, recyclerView, layoutManager)
                searchString = searchText.text.toString()
            }
        }
    }

    private fun displayAds(
        list: ArrayList<ResultAd>,
        searchText: EditText,
        recyclerView: RecyclerView,
        layoutManager: LinearLayoutManager
    ) {
        doAsync {
            val dataList = updateSearchList(list, searchText.text.toString())
            uiThread {
                progressBar_search.visibility = ProgressBar.INVISIBLE
                if (dataList.isEmpty())
                    toast("Объявлений не найдено")
                val adapter =
                    SearchAdapter(
                        updateSearchList(dataList, searchText.text.toString()),
                        object : SearchAdapter.OnItemClickListener {
                            override fun invoke(ad: ResultAd) {
                                startActivity<AdViewActivity>(
                                    "adId" to ad.id,
                                    "request" to searchText.text.toString()
                                )
                            }
                        })
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = adapter
                recyclerView.addOnScrollListener(
                    SearchAdapter.OnScrollListener(
                        layoutManager,
                        adapter, dataList,
                        searchText.text.toString()
                    )
                )
            }
        }
        progressBar_search.visibility = ProgressBar.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.finish()
            }
        }
        startActivity<MainActivity>()

        return true
    }
}
