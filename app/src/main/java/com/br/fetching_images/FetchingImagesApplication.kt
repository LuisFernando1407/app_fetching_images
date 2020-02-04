package com.br.fetching_images

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import timber.log.Timber

class FetchingImagesApplication: Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()

        /* Config Timber */
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        /* Set instance */
        context = applicationContext
    }
}