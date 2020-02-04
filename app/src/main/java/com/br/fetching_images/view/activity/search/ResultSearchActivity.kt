package com.br.fetching_images.view.activity.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.br.fetching_images.R
import com.br.fetching_images.model.Image
import com.br.fetching_images.view.adapter.NoResultsAdapter
import com.br.fetching_images.view.adapter.ResultSearchAdapter
import kotlinx.android.synthetic.main.activity_result_search.*

class ResultSearchActivity : AppCompatActivity() {

    private lateinit var resultSearchViewModel: ResultSearchViewModel
    private lateinit var images: List<Image>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_search)

        resultSearchViewModel = ViewModelProviders.of(
            this, ResultSearchViewModelFactory(this)
        ).get(ResultSearchViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        /* Sample call to fetch galleries by query */
        getResult("cats")
    }

    private fun getResult(query: String) {
        /*
            * I couldn't get images from a single gallery *

            Example of request to gallery images: GET/ https://api.imgur.com/3/gallery/image/{{galleryImageHash}}
            Response: 404 for any galleryImageHash
            --------------------------------------------------
            * So I took the images from each gallery of the endpoint: GET/ https://api.imgur.com/3/gallery/search?q={{query}}
        */
        resultSearchViewModel.getResult(query).observe(this, Observer { response ->
            if (response != null && response.data.isNotEmpty()) {
                images = ArrayList()
                /*
                * There are cases where there is no array of images and the image is already in the "link" field
                * */
                for(data in response.data){
                    if(data.images != null && data.images!!.isNotEmpty()){
                        for(image in data.images!!){
                            if (image.type.contains("image")) {
                                (images as ArrayList<Image>).add(
                                    image
                                )
                            }
                        }
                    }
                }

                rvResult.adapter = ResultSearchAdapter(this, images)
                rvResult.layoutManager = object : GridLayoutManager(this, 4) {
                    override fun canScrollVertically() : Boolean {
                        return false
                    }
                }
            } else {
                rvResult.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                rvResult.adapter = NoResultsAdapter(this, getString(R.string.title_no_results))
            }
        })
    }

    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.title_alert_exit))
            .setMessage(getString(R.string.subtitle_alert_exit))
            .setNegativeButton(getString(R.string.title_alert_exit_button_negative), null)
            .setPositiveButton(getString(R.string.title_alert_exit_button_positive)) { _, _ ->
                finish()
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }.create()

        alertDialog.setOnShowListener {
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.gray_app))

            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.colorPrimary))
        }

        alertDialog.show()
    }
}