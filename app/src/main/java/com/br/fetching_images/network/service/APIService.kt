package com.br.fetching_images.network.service

import com.br.fetching_images.network.constants.APIConstants
import com.br.fetching_images.network.response.SearchInGalleriesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiServices {
    /* Search in Galleries by Query */
    @Headers(APIConstants.CONTENT_TYPE)
    @GET(APIConstants.SEARCH_IN_GALLERY)
    fun galleriesByQuery(@Query("q") q: String): Call<SearchInGalleriesResponse>
}