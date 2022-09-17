package com.valu.myapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valu.myapplication.models.ApiState
import com.valu.myapplication.repository.ProductsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ProductsViewModel(private var productsRepository: ProductsRepository) : ViewModel() {

    // Instead of using live data using flow

     val pMassage: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)

    fun getProductsList() = viewModelScope.launch {
        pMassage.value = ApiState.Loading
        productsRepository.getProductsList()
            .catch { e ->
                pMassage.value = ApiState.Failure(e)
            }.collect { data ->
                pMassage.value = ApiState.Success(data)
            }
    } // getProductsList
}