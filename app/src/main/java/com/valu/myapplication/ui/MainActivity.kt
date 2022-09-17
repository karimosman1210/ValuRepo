package com.valu.myapplication.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.valu.myapplication.R
import com.valu.myapplication.adapter.ProductsOperatorAdapter
import com.valu.myapplication.databinding.ActivityMainBinding
import com.valu.myapplication.models.ApiState
import com.valu.myapplication.models.ProductsResponse
import com.valu.myapplication.repository.ProductsRepository
import com.valu.myapplication.viewModels.ProductsViewModel
import com.valu.myapplication.viewModels.ProductsViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var productsViewModel: ProductsViewModel
    private var items = arrayListOf<ProductsResponse>()
    private lateinit var productsOperatorAdapter: ProductsOperatorAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        productsViewModel = ViewModelProvider(
            this,
            ProductsViewModelFactory(ProductsRepository())
        )[ProductsViewModel::class.java]

        initUI()

        collects() // Like observers of live data

        listeners()

    }// onCreate

    private fun initUI() {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM;
        supportActionBar?.setCustomView(R.layout.toolbar_layout);
        productsOperatorAdapter = ProductsOperatorAdapter(items)
        binding.rvFlowers.adapter = productsOperatorAdapter
        binding.rvFlowers.layoutManager = GridLayoutManager(this, 2)

        productsViewModel.getProductsList()

        binding.srlFlowers.post {
            productsViewModel.getProductsList()
        }
        binding.srlFlowers.setOnRefreshListener {
            productsViewModel.getProductsList()

        }

    }

    private fun collects() {
        lifecycleScope.launchWhenCreated {
            productsViewModel.pMassage.collect {
                when (it) {
                    is ApiState.Loading -> {
                        binding.srlFlowers.isRefreshing = true

                    }
                    is ApiState.Failure -> {
                        it.e.printStackTrace()
                        binding.srlFlowers.isRefreshing = false

                    }
                    is ApiState.Success -> {
                        binding.srlFlowers.isRefreshing = false
                        val myObj = it.data as List<ProductsResponse>
                        items.clear()
                        items.addAll(myObj)
                        productsOperatorAdapter.notifyDataSetChanged()
                    }
                    is ApiState.Empty -> {
                        println("Empty...")
                    }
                }
            }
        }

    } // collects

    private fun listeners() {
        productsOperatorAdapter.setProductClick {
            showBottomSheetDialog(it)
        }
    }

    private fun showBottomSheetDialog(productsResponse: ProductsResponse) {

        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.button_sheet_dialog)

        val ivProduct = dialog.findViewById<ImageView>(R.id.ivProduct)
        val tvTitle = dialog.findViewById<TextView>(R.id.tvTitle)
        val tvDesc = dialog.findViewById<TextView>(R.id.tvDesc)
        val tvPrice = dialog.findViewById<TextView>(R.id.tvPrice)
        val ratingBar = dialog.findViewById<RatingBar>(R.id.ratingBar)

        tvTitle?.text = productsResponse.title
        tvDesc?.text = productsResponse.description
        tvPrice?.text = getString(R.string.price, productsResponse.price.toString())
        Glide.with(this).load(productsResponse.image).into(ivProduct!!)
        ratingBar?.rating = productsResponse.rating.rate?.toFloat()!!
        dialog.show()
    }
}