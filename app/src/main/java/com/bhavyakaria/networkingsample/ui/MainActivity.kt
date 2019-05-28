package com.bhavyakaria.networkingsample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.bhavyakaria.networkingsample.R
import androidx.lifecycle.ViewModelProviders
import com.bhavyakaria.networkingsample.utils.Resource
import com.bhavyakaria.networkingsample.utils.Status

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        viewModel.fetchPosts().observe(this, Observer {

            when (it?.status) {
                Status.SUCCESS -> {
                    Log.d("MainActivity", "---> Success in fetching data")
                }
                Status.ERROR -> {
                    Log.d("MainActivity", "---> Error while making network call")
                }
                Status.LOADING -> {
                    Log.d("MainActivity", "---> Loading...")
                }
            }
        })

    }
}
