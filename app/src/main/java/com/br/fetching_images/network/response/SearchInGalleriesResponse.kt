package com.br.fetching_images.network.response

import com.br.fetching_images.model.Gallery
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SearchInGalleriesResponse(
    @SerializedName("data")
    @Expose
    val data: List<Gallery>
)