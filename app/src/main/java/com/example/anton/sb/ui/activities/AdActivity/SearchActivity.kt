package com.example.anton.sb.ui.activities.AdActivity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import com.example.anton.sb.R
import com.example.anton.sb.data.Extensions.updateSearchList
import com.example.anton.sb.data.ResponseClasses.ResultAd
import com.example.anton.sb.ui.adapters.SearchAdapter
import kotlinx.android.synthetic.main.app_bar_search.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread


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

        if (intent.hasExtra("request")) {
            searchText.setText(intent.getStringExtra("request"))
            displayAds(list, searchText, recyclerView, layoutManager)
        }

        searchButton.setOnClickListener {
            if (searchText.text.isNullOrEmpty())
                toast("Вы ничего не вввели")
            else if (list.size == 0)
                displayAds(list, searchText, recyclerView, layoutManager)
        }
    }

    private fun displayAds(
        list: ArrayList<ResultAd>,
        searchText: EditText, recyclerView: RecyclerView,
        layoutManager: LinearLayoutManager
    ) {
        doAsync {
            val dataList = updateSearchList(list, searchText.text.toString())
            uiThread {
                if (dataList.isEmpty())
                    toast("Объявлений не найдено")
                val adapter =
                    SearchAdapter(
                        updateSearchList(dataList, searchText.text.toString()),
                        object : SearchAdapter.OnItemClickListener {
                            override fun invoke(ad: ResultAd) {
                                startAdViewActivity(ad.id, searchText.text.toString())
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
    }

    private fun startAdViewActivity(id: Long, string: String) {
        val intent = Intent(this, AdViewActivity::class.java)
        intent.putExtra("adId", id)
        intent.putExtra("request", string)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.finish()
            }
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        return true
    }
}
