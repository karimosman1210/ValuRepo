package com.valu.myapplication.models

import com.google.gson.annotations.SerializedName

data class RatingProducts(
    @SerializedName("rate"  ) var rate  : Double? ,
    @SerializedName("count" ) var count : Int?

)
