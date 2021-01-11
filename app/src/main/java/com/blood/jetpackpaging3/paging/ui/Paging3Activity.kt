package com.blood.jetpackpaging3.paging.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.blood.jetpackpaging3.R
import com.blood.jetpackpaging3.databinding.ActivityPaging3Binding
import com.blood.jetpackpaging3.paging.viewmodel.PagingViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class Paging3Activity : AppCompatActivity() {

    private lateinit var binding: ActivityPaging3Binding

    private lateinit var adapter: PagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_paging3)

        adapter = PagingAdapter()
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = adapter.withLoadStateFooter(LiveLoadStateAdapter(adapter::retry))
        binding.rv.setHasFixedSize(true)

        adapter.addLoadStateListener {
//            when (it.refresh) {
//                is LoadState.Loading -> log("refresh Loading")
//                is LoadState.NotLoading -> log("refresh NotLoading")
//                is LoadState.Error -> log("refresh Error")
//            }
            when (it.append) {
                is LoadState.Loading -> log("append Loading")
                is LoadState.NotLoading -> log("append NotLoading")
                is LoadState.Error -> log("append Error")
            }
        }

        val viewModel = ViewModelProvider(this).get(PagingViewModel::class.java)

//        viewModel.articleList.observe(this, {
//            lifecycleScope.launchWhenCreated {
//                adapter.submitData(it)
//            }
//        })

        lifecycleScope.launchWhenCreated {
//            viewModel.getPager().collectLatest {
//                adapter.submitData(it)
//            }
            viewModel.getPagerByRemote().collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.rv.scrollToPosition(0) }
        }
    }

    private fun log(msg: String) {
        Log.d("blood", "log: $msg")
    }

}