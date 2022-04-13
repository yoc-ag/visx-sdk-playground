package com.yoc.demoappshowcaseandroidkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yoc.demoappshowcaseandroidkotlin.databinding.ActivityMainBinding
import com.yoc.demoappshowcaseandroidkotlin.examples.interstitial.InterstitialActivity
import com.yoc.demoappshowcaseandroidkotlin.examples.universal_recycler_view.UniversalRecyclerViewActivity
import com.yoc.demoappshowcaseandroidkotlin.examples.universal_scroll_view.UniversalScrollViewActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()
    }

    private fun setupUi() {
        initButtons()
    }

    private fun initButtons() {
        binding.scrollViewButton.setOnClickListener {
            UniversalScrollViewActivity.start(this)
        }
        binding.recyclerViewButton.setOnClickListener {
            UniversalRecyclerViewActivity.start(this)
        }
        binding.interstitialButton.setOnClickListener {
            InterstitialActivity.start(this)
        }
    }
}