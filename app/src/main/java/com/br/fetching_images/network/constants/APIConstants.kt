package com.br.fetching_images.network.constants

object APIConstants {
    const val BASE_APP = "https://api.imgur.com"
    const val CONTENT_TYPE = "Content-Type: application/json"
    const val TYPE_AUTHORIZATION = "Client-ID "

    const val CLIENT_ID = "1ceddedc03a5d71"

    private const val API_VERSION = "3"
    private const val BASE_API = "/$API_VERSION"

    const val SEARCH_IN_GALLERIES = "$BASE_API/gallery/search"
}