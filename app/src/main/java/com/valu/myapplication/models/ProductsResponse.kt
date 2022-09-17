package com.valu.myapplication.models

import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("id"          ) var id          : Int?    ,
    @SerializedName("title"       ) var title       : String? ,
    @SerializedName("price"       ) var price       : Double?,
    @SerializedName("description" ) var description : String? ,
    @SerializedName("category"    ) var category    : String? ,
    @SerializedName("image"       ) var image       : String? ,
    @SerializedName("rating"      ) var rating      : RatingProducts


)
