package com.blood.jetpackpaging3.paging.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blood.jetpackpaging3.R
import com.blood.jetpackpaging3.databinding.LayoutItemArticleBinding
import com.blood.jetpackpaging3.paging.bean.DataX
import java.text.DateFormat

class PagingAdapter : PagingDataAdapter<DataX, PagingAdapter.ViewHolder>(ARTICLE_COMPARATOR) {

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<DataX>() {
            override fun areItemsTheSame(oldItem: DataX, newItem: DataX): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: DataX, newItem: DataX): Boolean =
                oldItem == newItem
        }
    }

    class ViewHolder(val binding: LayoutItemArticleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.position.text = position.toString()
        holder.binding.title.text = data?.title
        holder.binding.shareDate.text = DateFormat.getDateInstance().format(data?.shareDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<LayoutItemArticleBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_item_article,
            parent,
            false
        )
        return ViewHolder(binding)
    }

}