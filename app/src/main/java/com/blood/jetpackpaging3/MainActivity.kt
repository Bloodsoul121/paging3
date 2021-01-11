package com.blood.jetpackpaging3

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blood.jetpackpaging3.differ.AsyncListDifferActivity
import com.blood.jetpackpaging3.paging.ui.Paging3Activity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun clickBtn1(view: View) {
        startActivity(Intent(this, AsyncListDifferActivity::class.java))
    }

    fun clickBtn2(view: View) {
        startActivity(Intent(this, Paging3Activity::class.java))
    }
}