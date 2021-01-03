package com.blood.jetpackpaging3.differ

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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
class DifferAdapter : RecyclerView.Adapter<DifferAdapter.ViewHolder>() {

    private val datas = mutableListOf<User>()

    class ViewHolder(val binding: LayoutItemDifferBinding) :
        RecyclerView.ViewHolder(binding.root) {}

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
        val user = datas[position]
        holder.binding.text.text = "item - ${user.name} - ${user.age}"
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    fun submit(list: List<User>) {
        datas.clear()
        datas.addAll(list)
        notifyDataSetChanged()
    }

}