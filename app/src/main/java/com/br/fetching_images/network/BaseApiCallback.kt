package com.br.fetching_images.network

import android.app.Dialog
import android.content.Context
import android.content.Intent
import com.br.fetching_images.R
import com.br.fetching_images.util.alert
import com.br.fetching_images.util.loadingDialog
import com.br.fetching_images.view.activity.error.ServerErrorActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

open class BaseCallbackApi<T>(private val context: Context) : Callback<T> {
    private lateinit var dialog: Dialog

    private companion object {
        const val INTENT_MESSAGE_KEY: String = "message"
    }

    init {
        OnStartLoading()
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        OnStopLoading()
        whenConnectTimeOut(t)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        OnStopLoading()
        alertStatusCode(response.code())
    }

    private fun OnStopLoading() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }

    private fun OnStartLoading() {
        dialog = loadingDialog(context)
        dialog.show()
    }

    private fun alertStatusCode(statusCode: Int) {
        when(statusCode){
            500 ->  alert(
                context,
                context.resources.getString(R.string.title_alert_api_error),
                context.resources.getString(R.string.title_alert_status_code_500)
            )
            404 -> alert(
                context,
                context.resources.getString(R.string.title_alert_api_error),
                context.resources.getString(R.string.title_alert_status_code_404)
            )
            403 -> alert(
                context,
                context.resources.getString(R.string.title_alert_api_error),
                context.resources.getString(R.string.title_alert_status_code_403)
            )
        }
    }

    private fun whenConnectTimeOut(throwable: Throwable) {
        if (throwable is SocketTimeoutException) {
            val intent = Intent(context, ServerErrorActivity::class.java)
            intent.putExtra(
                INTENT_MESSAGE_KEY,
                context.resources.getString(R.string.title_alert_throwable_timeout)
            )
            context.startActivity(intent)
        }

        if (throwable is ConnectException) {
            val intent = Intent(context, ServerErrorActivity::class.java)
            intent.putExtra(
                INTENT_MESSAGE_KEY,
                context.resources.getString(R.string.title_alert_throwable_connect_exception)
            )
            context.startActivity(intent)
        }
    }
}