package com.example.calcount

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.calcount.Screens.DatePac.DateScreen
import com.example.calcount.Screens.ItemsPac.ItemsScreen

@Composable
fun navHost(innerPadding : PaddingValues){
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.DateList.route){
        composable(Screen.DateList.route){
            DateScreen(navController,innerPadding)
        }
        composable(
            route = Screen.ItemsList.route, // e.g., "itemslist/{id}"
            arguments = listOf(navArgument("id") { type = NavType.IntType })
//            "itemslist/{id}"
        ) {
            backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")?:0
            ItemsScreen(id,innerPadding,navController)
        }

    }
}