package com.example.calcount.Screens.ItemsPac

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calcount.Data.Local.CalItems
import com.example.calcount.Data.Remote.FoodSummary
import com.example.calcount.Data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ViewModelItems @Inject constructor(private val repository : Repository) : ViewModel(){

    private val _items = MutableStateFlow<List<FoodSummary>>(emptyList())
    val items : StateFlow<List<FoodSummary>> = _items

    private val _selectedItems = MutableStateFlow<List<CalItems>>(emptyList())
    val selectedItems : StateFlow<List<CalItems>> = _selectedItems

    fun searchItems(query : String,apiKey : String){
        viewModelScope.launch{
            val ItemsList =repository.searchFood(query,apiKey)
            _items.value = ItemsList
        }
    }

    fun slectedItem(fdcId : Long,apiKey: String,dateId : Int){
        viewModelScope.launch {
            repository.fetchFoodCalories(fdcId , apiKey,dateId)
        }
        viewModelScope.launch {
            repository.getItems(dateId)
                .collect { item ->
                    _selectedItems.value = item
                }
        }
    }
}