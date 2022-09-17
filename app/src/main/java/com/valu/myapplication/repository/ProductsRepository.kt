package com.valu.myapplication.repository

import com.valu.myapplication.models.ProductsResponse
import com.valu.myapplication.networkService.RetroInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductsRepository  {
    fun getProductsList(): Flow<List<ProductsResponse>> = flow {
        val data = RetroInstance.getRetroInstance().getProductFromApi()
        emit(data)
    }.flowOn(Dispatchers.IO)
}