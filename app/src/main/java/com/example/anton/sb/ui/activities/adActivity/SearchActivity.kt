package com.example.anton.sb.ui.activities.adActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import com.example.anton.sb.R
import com.example.anton.sb.model.ResultAd
import com.example.anton.sb.service.updateSearchList
import com.example.anton.sb.ui.adapters.SearchAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.app_bar_search.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar

/**
 * A screen searching ad
 *
 *@author Anton Kirichenkov
 */
class SearchActivity : AppCompatActivity() {

    /**
     * @property requestad
     */

    /**
     * searching line
     */
    private var request: String = ""

    /**
     * @suppress
     */
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

        searchText.setOnKeyListener{ _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                when {
                    searchText.text.isNullOrEmpty() -> this.contentView?.snackbar("Вы ничего не ввели")
                    list.size == 0 -> {
                        searchString = searchText.text.toString()
                        displayAds(list, searchText, recyclerView, layoutManager)
                    }
                    searchText.text.toString() != searchString -> {
                        list.clear()
                        displayAds(list, searchText, recyclerView, layoutManager)
                        searchString = searchText.text.toString()
                    }
                }
                return@setOnKeyListener true
            }
            false
        }

        searchButton.setOnClickListener {
            when {
                searchText.text.isNullOrEmpty() -> it.snackbar("Вы ничего не ввели")
                list.size == 0 -> {
                    searchString = searchText.text.toString()
                    displayAds(list, searchText, recyclerView, layoutManager)
                }
                searchText.text.toString() != searchString -> {
                    list.clear()
                    displayAds(list, searchText, recyclerView, layoutManager)
                    searchString = searchText.text.toString()
                }
            }
        }
    }

    /**
     * Method for display searching ads with recyclerView Adapter.
     * If have problems with connection server or no ads,
     * then display message "Объявлений нет".
     *
     * @param list [ArrayList]
     * @param searchText
     * @param recyclerView
     * @param layoutManager
     *
     * @see [updateSearchList]
     * @see [SearchAdapter]
     */
    private fun displayAds(
        list: ArrayList<ResultAd>,
        searchText: EditText,
        recyclerView: RecyclerView,
        layoutManager: LinearLayoutManager
    ) {
        doAsync {
            val dataList = updateSearchList(list, searchText.text.toString(), this@SearchActivity)
            uiThread {
                progressBar_search.visibility = ProgressBar.INVISIBLE
                val adapter =
                    SearchAdapter(
                        dataList,
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
                        searchText.text.toString(),
                        this@SearchActivity
                    )
                )
            }
        }
        progressBar_search.visibility = ProgressBar.VISIBLE
    }

    /**
     * @suppress
     */
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
