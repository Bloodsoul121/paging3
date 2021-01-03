package com.blood.jetpackpaging3.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blood.jetpackpaging3.R
import com.blood.jetpackpaging3.databinding.LayoutItemArticleBinding
import java.text.DateFormat


/*
 *  @项目名：  JetpackPaging3 
 *  @包名：    com.blood.jetpackpaging3.paging
 *  @文件名:   PagingAdapter
 *  @创建者:   bloodsoul
 *  @创建时间:  2021/1/3 17:32
 *  @描述：    TODO
 */
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