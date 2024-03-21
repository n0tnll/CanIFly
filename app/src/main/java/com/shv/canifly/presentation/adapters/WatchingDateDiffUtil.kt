package com.shv.canifly.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.shv.canifly.domain.entity.WatchingDate

class WatchingDateDiffUtil : DiffUtil.ItemCallback<WatchingDate>() {
    override fun areItemsTheSame(oldItem: WatchingDate, newItem: WatchingDate): Boolean {
        return newItem.dateTime == oldItem.dateTime
    }

    override fun areContentsTheSame(oldItem: WatchingDate, newItem: WatchingDate): Boolean {
        return newItem == oldItem
    }
}