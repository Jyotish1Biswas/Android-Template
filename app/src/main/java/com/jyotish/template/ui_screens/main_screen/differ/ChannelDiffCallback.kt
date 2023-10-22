package com.jyotish.template.ui_screens.main_screen.differ

import androidx.recyclerview.widget.DiffUtil
import com.jyotish.template.network.model.TestResponse

object ChannelDiffCallback : DiffUtil.ItemCallback<TestResponse.Data>() {
    override fun areItemsTheSame(
        oldItem: TestResponse.Data,
        newItem: TestResponse.Data
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: TestResponse.Data,
        newItem: TestResponse.Data
    ): Boolean {
        return oldItem == newItem
    }
}