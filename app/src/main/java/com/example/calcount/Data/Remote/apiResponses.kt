package com.example.calcount.Data.Remote

import com.google.gson.annotations.SerializedName

data class FoodSearchResponse(
   val foods : List<FoodSummary>
)

data class FoodSummary(
    val fdcId : Long,
    val description : String
)

data class FoodDetail(
    val description: String,
    val foodNutrients: List<Nutrient>
)

data class Nutrient(
    val amount: Double?,
    val nutrient: NutrientInfo? = null
)

data class NutrientInfo(
    val name: String?,
    val unitName: String?
)


