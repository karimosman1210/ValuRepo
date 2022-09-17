package com.valu.myapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.valu.myapplication.repository.ProductsRepository

class ProductsViewModelFactory(private val productsRepository: ProductsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return ProductsViewModel(productsRepository) as T
    }
}
