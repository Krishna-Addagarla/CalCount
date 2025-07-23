package com.example.calcount

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calcount.Screens.DatePac.DateScreen
import com.example.calcount.Screens.ItemsPac.ItemsScreen

@Composable
fun navHost(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.DateList.route){
        composable(Screen.DateList.route){
            DateScreen(navController)
        }
        composable("itemslist/{id}") {
            backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")?:0
            ItemsScreen(id,navController)
        }

    }
}