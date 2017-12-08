package com.github.putraxor.outstagram

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.nitrico.lastadapter.LastAdapter
import com.github.putraxor.outstagram.databinding.FilterItemBinding
import kotlinx.android.synthetic.main.activity_filter.*
import org.insta.*

class FilterActivity : AppCompatActivity() {
    private val TAG: String = this::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        setupFilter()
    }


    private fun setupFilter() {
        val imageUriString = intent.extras.getString("uri")
        val imageUri = Uri.parse(imageUriString)
        Log.d(TAG, "Get uri from intent $imageUri")
        filterImage.setImage(imageUri)

        val filters = listOf(
                FilterItem("Normal", IFNormalFilter(this)),
                FilterItem("Hudson", IFHudsonFilter(this)),
                FilterItem("X-Pro", IFXproIIFilter(this)),
                FilterItem("Sierra", IFSierraFilter(this)),
                FilterItem("Amaro", IFAmaroFilter(this)),
                FilterItem("Rise", IFRiseFilter(this)),
                FilterItem("Lomo", IFLomofiFilter(this))
        )

        LastAdapter(filters, BR.item)
                .map<FilterItem, FilterItemBinding>(R.layout.filter_item) {
                    onClick {
                        val filter = it.binding.item
                        if (filter != null) {
                            filterImage.filter = filter.filterClass
                        }
                    }
                }
                .into(filterOption)
    }
}
