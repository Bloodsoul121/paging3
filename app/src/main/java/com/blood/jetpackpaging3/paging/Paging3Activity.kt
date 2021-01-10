package com.blood.jetpackpaging3.paging

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.blood.jetpackpaging3.R
import com.blood.jetpackpaging3.databinding.ActivityPaging3Binding

class Paging3Activity : AppCompatActivity() {

    private lateinit var binding: ActivityPaging3Binding

    private lateinit var adapter: PagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_paging3)

        adapter = PagingAdapter()
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = adapter

        adapter.addLoadStateListener {
            when(it.refresh) {
                is LoadState.Loading -> log("refresh Loading")
                is LoadState.NotLoading -> log("refresh NotLoading")
                is LoadState.Error -> log("refresh Error")
            }
            when(it.append) {
                is LoadState.Loading -> log("append Loading")
                is LoadState.NotLoading -> log("append NotLoading")
                is LoadState.Error -> log("append Error")
            }
        }

        val viewModel = ViewModelProvider(this).get(PagingViewModel::class.java)
        viewModel.articleList.observe(this, {
            lifecycleScope.launchWhenCreated {
                adapter.submitData(it)
            }
        })
    }

    private fun log(msg : String) {
        Log.d("Paging3", "log: $msg")
    }

}