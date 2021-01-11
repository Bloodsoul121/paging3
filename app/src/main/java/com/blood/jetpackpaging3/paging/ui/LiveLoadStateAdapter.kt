package com.blood.jetpackpaging3.paging.ui

import android.graphics.drawable.AnimationDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.blood.jetpackpaging3.R
import com.blood.jetpackpaging3.databinding.LayoutItemLiveLoadStateBinding
import com.utalk.hsing.room.live.base.BindingViewHolder

class LiveLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<BindingViewHolder<LayoutItemLiveLoadStateBinding>>() {

    override fun onBindViewHolder(
        holder: BindingViewHolder<LayoutItemLiveLoadStateBinding>,
        loadState: LoadState
    ) {
        when (loadState) {
            is LoadState.Loading -> {
                holder.binding.footerLoadMore.visibility = View.VISIBLE
                (holder.binding.footerLoadMore.background as AnimationDrawable).start()
                holder.binding.footerTip.text = "加載中，請稍後..."
                holder.binding.footerTip.setOnClickListener(null)
            }
            is LoadState.NotLoading -> {
                holder.binding.footerLoadMore.visibility = View.GONE
                (holder.binding.footerLoadMore.background as AnimationDrawable).stop()
                holder.binding.footerTip.text = null
                holder.binding.footerTip.setOnClickListener(null)
            }
            is LoadState.Error -> {
                holder.binding.footerLoadMore.visibility = View.GONE
                (holder.binding.footerLoadMore.background as AnimationDrawable).stop()
                holder.binding.footerTip.text = "點按重新加載"
                holder.binding.footerTip.setOnClickListener { retry.invoke() }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): BindingViewHolder<LayoutItemLiveLoadStateBinding> {
        val binding = DataBindingUtil.inflate<LayoutItemLiveLoadStateBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_item_live_load_state,
            parent, false
        )
        return BindingViewHolder(binding)
    }

}