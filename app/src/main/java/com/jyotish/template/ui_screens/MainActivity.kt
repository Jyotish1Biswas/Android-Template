package com.jyotish.template.ui_screens

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jyotish.template.R
import com.jyotish.template.databinding.ActivityMainBinding
import com.jyotish.template.helper.makeToast
import com.jyotish.template.network.ResponseState
import com.jyotish.template.ui_screens.main_screen.DemoAdapter
import com.jyotish.template.ui_screens.main_screen.DemoViewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var binding: ActivityMainBinding
    private val viewModel:DemoViewModel by viewModels()
    private val adapter: DemoAdapter by lazy { DemoAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        getDemoResponse()
    }

    private fun getDemoResponse() {
        viewModel.getDemoApi("C").observe(this) {
            when(it){
                is ResponseState.Error ->  {
                    it.errorMessage?.let { it1 -> Log.e("error", it1) }
                    makeToast(it.errorMessage)
                }
                ResponseState.Loading -> {
                    makeToast("loading")
                }
                is ResponseState.Success -> {
                    adapter.submitItems(it.data.data)
                    makeToast("success")
                    Log.e("success data: ", it.toString())
                }
            }
        }
    }

    private fun setupObserver() {
        viewModel.testResponse.observe(this){
            when(it) {
                is ResponseState.Error ->  {
                    makeToast(it.errorMessage)
                }
                ResponseState.Loading -> {

                }
                is ResponseState.Success -> {
                    makeToast("success")
                }
            }
        }
    }

    private fun initRecyclerView() {

        binding.channelRecycler.adapter = adapter

        adapter.scrollToTopListener = {
            binding.channelRecycler.smoothScrollToPosition(0)
        }

        adapter.onItemClick = { match ->
            Log.e("onItemClick", "Click")
            makeToast(match.titleShort)
        }
    }
}