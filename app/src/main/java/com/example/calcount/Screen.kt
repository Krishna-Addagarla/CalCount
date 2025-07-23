package com.example.calcount

sealed class Screen(val route : String) {
    object DateList : Screen("date_list")
    object ItemsList : Screen("itemslist/{id}"){
        fun createRoute(id : Int) = "itemslist/$id"
    }
}