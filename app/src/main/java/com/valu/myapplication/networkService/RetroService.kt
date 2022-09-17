package com.valu.myapplication.networkService

import com.valu.myapplication.models.ProductsResponse
import retrofit2.http.GET

interface RetroService {

    @GET(AllApi.PRODUCTS)
    suspend fun getProductFromApi(): List<ProductsResponse>

}