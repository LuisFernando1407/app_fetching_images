package com.br.fetching_images.network.request

import com.br.fetching_images.network.auth.Authenticated
import com.br.fetching_images.network.service.ApiServices

class APIRequest: Authenticated() {

    init {
        setupRetrofit()
    }

    val services: ApiServices
        get() = retrofit!!.create(ApiServices::class.java)
}