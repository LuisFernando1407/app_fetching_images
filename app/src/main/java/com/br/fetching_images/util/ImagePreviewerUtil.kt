package com.br.fetching_images.util

import android.content.Context
import android.renderscript.Allocation
import android.renderscript.ScriptIntrinsicBlur
import android.renderscript.RenderScript
import android.renderscript.Element
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import kotlin.math.roundToInt

object ImagePreviewerUtil {
    fun getBlurredScreenDrawable(context: Context, screen: View): BitmapDrawable {
        val screenshot = takeScreenshot(screen)!!
        val blurred = blurBitmap(context, screenshot)
        return BitmapDrawable(context.resources, blurred)
    }

    private fun takeScreenshot(view: View): Bitmap? {
        view.isDrawingCacheEnabled = true
        view.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_LOW
        view.buildDrawingCache()
        if (view.drawingCache == null) return null
        val snapshot = Bitmap.createBitmap(view.drawingCache)
        view.isDrawingCacheEnabled = false
        view.destroyDrawingCache()
        return snapshot
    }

    private fun blurBitmap(context: Context, bitmap: Bitmap): Bitmap {
        val bitmapScale = 0.3f
        val blurRadius = 10f

        val width = (300 * bitmapScale).roundToInt()
        val height = (300 * bitmapScale).roundToInt()

        val inputBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
        val outputBitmap = Bitmap.createBitmap(inputBitmap)

        val rs = RenderScript.create(context)
        val theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
        val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)
        theIntrinsic.setRadius(blurRadius)
        theIntrinsic.setInput(tmpIn)
        theIntrinsic.forEach(tmpOut)
        tmpOut.copyTo(outputBitmap)

        return outputBitmap
    }
}