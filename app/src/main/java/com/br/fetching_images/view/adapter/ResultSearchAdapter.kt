package com.br.fetching_images.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.br.fetching_images.R
import com.br.fetching_images.model.Image
import com.br.fetching_images.util.ImagePreviewerDialogUtil
import com.br.fetching_images.util.getProgressDrawable
import com.br.fetching_images.util.loadImage

class ResultSearchAdapter(val context: Context, private val images: List<Image>)
    :  RecyclerView.Adapter<ResultSearchAdapter.RecyclerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate( R.layout.adapter_item_result_search, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val image = images[position]

        /* Set image */
        holder.imvSearch.loadImage(image.link, getProgressDrawable(context))

        /* Preview image */
        holder.imvSearch.setOnClickListener { v ->
            ImagePreviewerDialogUtil().show(v.context, v as AppCompatImageView)
        }

    }

    override fun getItemCount() = images.size

    class RecyclerViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val imvSearch: AppCompatImageView = itemView.findViewById(R.id.imvSearch)
    }
}