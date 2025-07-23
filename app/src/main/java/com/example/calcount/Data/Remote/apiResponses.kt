package com.example.calcount.Data.Remote

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
    val nutrientName: String,
    val value: Double
)

