package com.example.calcount.Data.Remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("fdc/v1/foods/search")
    suspend fun searchFoods(
        @Query("query") query : String,
        @Query("api_key") apiKey : String
    ) : FoodSearchResponse

    @GET("fdc/v1/food/{fdcId}")
    suspend fun searchItem(
        @Path("fdcId") fdcId : Long,
        @Query("api_key") apiKey : String
    ) : FoodDetail

}