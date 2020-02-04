package com.br.fetching_images.network.auth

import com.br.fetching_images.network.constants.APIConstants
import com.br.fetching_images.util.getApiToken
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class Authenticated {
    protected var retrofit: Retrofit? = null

    private val CONNECTION_TIMEOUT = 20 * 1000

    private val client = OkHttpClient.Builder().addInterceptor { chain ->
        var newRequest = chain.request()

        if (getApiToken() != null) {
            newRequest = chain.request().newBuilder()
                .addHeader("Authorization", APIConstants.TYPE_AUTHORIZATION + getApiToken())
                .build()
        }

        chain.proceed(newRequest)
    }.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES).build()

    protected fun setupRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl(APIConstants.BASE_APP)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}