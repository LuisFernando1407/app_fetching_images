package com.br.fetching_images.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.br.fetching_images.R

class NoResultsAdapter(val context: Context, private val title: String)
    :  RecyclerView.Adapter<NoResultsAdapter.RecyclerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate( R.layout.adapter_item_no_result, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.tvTitle.text = title
    }

    override fun getItemCount() = 1

    class RecyclerViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvTitle: AppCompatTextView = itemView.findViewById(R.id.tvTitle)
    }
}