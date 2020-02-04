package com.br.fetching_images.util

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageView
import com.br.fetching_images.R
import android.widget.Toast
import android.graphics.Bitmap
import android.os.Environment.DIRECTORY_PICTURES
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImagePreviewerDialogUtil {

    @SuppressLint("ClickableViewAccessibility", "InflateParams")
    fun show(context: Context, source: AppCompatImageView) {
        val background = ImagePreviewerUtil.getBlurredScreenDrawable(context, source.rootView)

        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_preview_image, null)

        val imvPreviewer = dialogView.findViewById<AppCompatImageView>(R.id.imvPreviewer)
        val close = dialogView.findViewById<AppCompatImageView>(R.id.close)

        if(source.drawable.constantState == null){
            Toast.makeText(context, context.getString(R.string.title_wait_for_loading), Toast.LENGTH_SHORT).show()
            return
        }

        val copy = source.drawable.constantState!!.newDrawable()

        /* If you want to save to your device */
        //val bm = drawableToBitmap(copy)
        //action onClickListener: save(context, bm, fileName)

        imvPreviewer.setImageDrawable(copy)

        val dialog = Dialog(context, R.style.ImagePreviewerTheme)
        dialog.window!!.setBackgroundDrawable(background)
        dialog.setContentView(dialogView)
        dialog.show()

        close.setOnClickListener{ dialog.dismiss() }
    }

    private fun save(context: Context, bm: Bitmap, fileName: String) {
        val extStorageDirectory =
            "${Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES)}/${context.getString(R.string.app_name)}"
        val file = File(extStorageDirectory, fileName)
        try {
            val outStream = FileOutputStream(file)
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            outStream.flush()
            outStream.close()

            Toast.makeText(context, context.getString(R.string.title_image_save), Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, context.getString(R.string.title_image_error), Toast.LENGTH_SHORT).show()
        }
    }
}