package com.example.calcount.Data

import com.example.calcount.Data.Local.CalDB
import com.example.calcount.Data.Local.CalDate
import com.example.calcount.Data.Local.CalDateDao
import com.example.calcount.Data.Local.CalItems
import com.example.calcount.Data.Local.CalItemsDao
import com.example.calcount.Data.Remote.ApiService
import com.example.calcount.Data.Remote.FoodSummary
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import javax.inject.Inject

class Repository @Inject constructor(
    private val calItemsDao :CalItemsDao,
    private val calDateDao: CalDateDao,
    private val api: ApiService) {

    fun getDates() : Flow<List<CalDate>>{
        return calDateDao.getCalByDate()
    }

    suspend fun insertDates(dates: List<CalDate>) {
        calDateDao.insertDates(dates)
    }



    fun getItems(dateid : Int) : Flow<List<CalItems>> {
        return calItemsDao.getItems(dateid)
    }

    suspend fun deleteItem(id : Int){
        calItemsDao.deleteItem(id)
    }

    suspend fun updateTotalCal(){
        calDateDao.updateTotalCal()
    }

    //For Api Calls
    suspend fun searchFood(query : String, apiKey : String) : List<FoodSummary>{
        return api.searchFoods(query,apiKey).foods
    }

    suspend fun fetchFoodCalories(fdcId : Long, apiKey: String,dateId : Int){
        val fdcId = fdcId
        val detail = api.searchItem(fdcId,apiKey)

        val calories = detail.foodNutrients
            .firstOrNull{it.nutrient?.name.equals("Energy",ignoreCase = true)}
            ?.amount ?: 0.0

        val calItem = CalItems(itemName = detail.description, itemCal = calories,dateid = dateId)
        calItemsDao.addItem(calItem)
        calDateDao.updateTotalCal()

    }
}