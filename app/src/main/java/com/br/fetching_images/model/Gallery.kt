package com.br.fetching_images.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Gallery(
    val id: String,

    @SerializedName("images")
    @Expose
    var images: List<Image>? = null
)