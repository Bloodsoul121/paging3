package com.blood.jetpackpaging3.differ

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.blood.jetpackpaging3.R
import com.blood.jetpackpaging3.databinding.ActivityAsyncListDifferBinding

class AsyncListDifferActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAsyncListDifferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_async_list_differ)
        init()
    }

    private fun init() {
        val adapter = DifferAdapter2()
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = adapter

        binding.init.setOnClickListener {
            val list = mutableListOf<User>()
            for (num in 1..100) {
                list.add(User(num, "user$num", num))
            }
            adapter.submit(list)
        }

        binding.refresh.setOnClickListener {
            val list = mutableListOf<User>()
            for (num in 1..100) {
                list.add(User(num, "user$num", if (num % 2 == 0) num else num * 2))
            }
            adapter.submit(list)
        }
    }
}