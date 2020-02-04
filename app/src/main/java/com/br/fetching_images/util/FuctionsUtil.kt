package com.br.fetching_images.util

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.br.fetching_images.FetchingImagesApplication
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.br.fetching_images.R


fun loadingDialog(ctx: Context): Dialog {
    val loading = Dialog(ctx)
    loading.requestWindowFeature(Window.FEATURE_NO_TITLE)
    loading.setContentView(R.layout.dialog_loading_default)
    loading.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    loading.setCanceledOnTouchOutside(false)
    loading.setCancelable(false)
    return loading
}

fun alert(ctx: Context, title: String, message: String) {
    val alertDialogBuilder = android.app.AlertDialog.Builder(ctx)
    val alertDialog = alertDialogBuilder.create()
    alertDialog.setTitle(title)
    alertDialog.setMessage(message)
    alertDialog.show()
}

private fun getSessionPreferences(): SharedPreferences {
    val ctx = FetchingImagesApplication.context
    return ctx!!.getSharedPreferences("SESSION_PREFERENCES", Context.MODE_PRIVATE)
}

fun getApiToken(): String? {
    return getSessionPreferences().getString("API_TOKEN", null)
}

fun setApiToken(token: String?) {
    val mPreferences = getSessionPreferences()
    val editor = mPreferences.edit()
    editor.putString("API_TOKEN", token)
    editor.apply()
}

fun drawableToBitmap(drawable: Drawable): Bitmap {
    val bitmap: Bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
        Bitmap.createBitmap(
            1,
            1,
            Bitmap.Config.ARGB_8888
        ) // Single color bitmap will be created of 1x1 pixel
    } else {
        Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
    }

    if (drawable is BitmapDrawable) {
        if (drawable.bitmap != null) {
            return drawable.bitmap
        }
    }

    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}