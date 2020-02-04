package com.br.fetching_images.view.activity.search

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.br.fetching_images.network.BaseCallbackApi
import com.br.fetching_images.network.request.APIRequest
import com.br.fetching_images.network.response.SearchInGalleriesResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

internal class ResultSearchViewModel(private val context: Context) : ViewModel() {

    private var searchMutableLiveData: MutableLiveData<SearchInGalleriesResponse> = MutableLiveData()
    private val request: APIRequest = APIRequest()

    fun getResult(query: String): LiveData<SearchInGalleriesResponse> {
        /* Search by Query in Galleries */
        /* Return list of gallery */
        val callResult = request.services.galleriesByQuery(query)

        callResult.enqueue(object : BaseCallbackApi<SearchInGalleriesResponse>(context) {
            override fun onResponse(
                call: Call<SearchInGalleriesResponse>,
                response: Response<SearchInGalleriesResponse>
            ) {
                super.onResponse(call, response)

                if (response.isSuccessful) {
                    searchMutableLiveData.setValue(response.body())
                } else {
                    searchMutableLiveData.setValue(null)
                }
            }
        })

        return searchMutableLiveData
    }
}