package com.blood.jetpackpaging3.paging.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blood.jetpackpaging3.MainApplication
import com.blood.jetpackpaging3.R
import com.blood.jetpackpaging3.databinding.LayoutItemArticleBinding
import com.blood.jetpackpaging3.paging.bean.DataX
import java.text.DateFormat

class PagingAdapter : PagingDataAdapter<DataX, PagingAdapter.ViewHolder>(ARTICLE_COMPARATOR),
    View.OnClickListener {

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
        holder.binding.root.tag = holder
        holder.binding.root.setOnClickListener(this)
        holder.binding.position.text = position.toString()
        holder.binding.id.text = data?.id.toString()
        holder.binding.title.text = data?.title?.trim().toString()
        holder.binding.shareDate.text = DateFormat.getDateInstance().format(data?.shareDate)
        holder.binding.executePendingBindings()
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

    override fun onClick(v: View?) {
        val holder = v?.tag as ViewHolder
        val position = holder.absoluteAdapterPosition
        Log.i("bloodsoul", "onClick: $position / $itemCount --> ${getItem(position)}")
        Toast.makeText(
            MainApplication.getContext(),
            "$position / $itemCount\r\n${getItem(position)?.title}",
            Toast.LENGTH_SHORT
        ).show()
    }

}