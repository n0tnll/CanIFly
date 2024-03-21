package com.shv.canifly.presentation.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.shv.canifly.domain.entity.WatchingDate

class WatchingDateAdapter :
    ListAdapter<WatchingDate, WatchingDateViewHolder>(WatchingDateDiffUtil()) {

    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchingDateViewHolder {
        context = parent.context
        return WatchingDateViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WatchingDateViewHolder, position: Int) {
        val watchingDate = getItem(position)
        holder.bind(watchingDate, context)
    }
}