package com.blood.jetpackpaging3.paging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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

        val viewModel = ViewModelProvider(this).get(PagingViewModel::class.java)
        viewModel.articleList.observe(this, {
            lifecycleScope.launchWhenCreated {
                adapter.submitData(it)
            }
        })
    }

}