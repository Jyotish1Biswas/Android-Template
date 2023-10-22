package com.jyotish.template.ui_screens.main_screen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.jyotish.template.databinding.ListItemTypeOneBinding
import com.jyotish.template.databinding.ListItemTypeThreeBinding
import com.jyotish.template.databinding.ListItemTypeTwoBinding
import com.jyotish.template.network.model.TestResponse
import com.jyotish.template.ui_screens.main_screen.differ.ChannelDiffCallback

class DemoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onItemClick: ((TestResponse.Data) -> Unit)? = null
    var scrollToTopListener: (() -> Unit)? = null
    private val differ = AsyncListDiffer(this, ChannelDiffCallback)

    fun submitItems(updatedList: List<TestResponse.Data>, scrollToTop: Boolean = false) {
        differ.submitList(updatedList.map { it.copy() }) {
            if (scrollToTop) {
                scrollToTopListener?.invoke()
            }
        }
    }

    fun replaceItem(item: TestResponse.Data) {
        val updatedList = differ.currentList.map {
            if (it.id == item.id) {
                item.copy()
            } else {
                it.copy()
            }
        }
        differ.submitList(updatedList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                val binding = ListItemTypeOneBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TypeOneViewHolder(binding, onItemClick)
            }
            2 -> {
                val binding = ListItemTypeTwoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TypeTwoViewHolder(binding, onItemClick)
            }
            else -> {
                val binding = ListItemTypeThreeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TypeThreeViewHolder(binding, onItemClick)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TypeOneViewHolder -> {
                holder.bind(differ.currentList[position])
            }
            is TypeTwoViewHolder -> {
                holder.bind(differ.currentList[position])
            }

            is TypeThreeViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (differ.currentList[position].formatStr) {
            "T20I" -> 1
            "ODI" -> 2
            else -> 3
        }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class TypeOneViewHolder(
        private val binding: ListItemTypeOneBinding,
        private val onItemClick: ((TestResponse.Data) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: TestResponse.Data) {
           binding.tittle.text = item.subtitle
            binding.root.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }

    class TypeTwoViewHolder(
        private val binding: ListItemTypeTwoBinding,
        private val onItemClick: ((TestResponse.Data) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: TestResponse.Data) {
            binding.tittle.text = item.subtitle
            binding.root.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }

    class TypeThreeViewHolder(
        private val binding: ListItemTypeThreeBinding,
        private val onItemClick: ((TestResponse.Data) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: TestResponse.Data) {
            binding.tittle.text = item.teamALogo
            binding.root.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }

}