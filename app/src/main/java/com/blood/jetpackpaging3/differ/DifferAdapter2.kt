package com.blood.jetpackpaging3.differ

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blood.jetpackpaging3.R
import com.blood.jetpackpaging3.databinding.LayoutItemDifferBinding


/*
 *  @项目名：  JetpackPaging3 
 *  @包名：    com.blood.jetpackpaging3.differ
 *  @文件名:   DifferAdapter
 *  @创建者:   bloodsoul
 *  @创建时间:  2021/1/3 15:24
 *  @描述：    TODO
 */
class DifferAdapter2 : RecyclerView.Adapter<DifferAdapter2.ViewHolder>() {

    class ViewHolder(val binding: LayoutItemDifferBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    private var differ: AsyncListDiffer<User> =

        AsyncListDiffer(this, object : DiffUtil.ItemCallback<User>() {

            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return (oldItem.name == newItem.name) && oldItem.age == newItem.age
            }

        })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<LayoutItemDifferBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_item_differ,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.binding.text.text = "item - ${user.name} - ${user.age}"
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private fun getItem(position: Int): User {
        return differ.currentList[position]
    }

    fun submit(list: List<User>) {
        differ.submitList(list)
    }

}