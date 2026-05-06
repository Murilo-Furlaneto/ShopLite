package br.com.furlaneto.murilo.shoplite.route

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.furlaneto.murilo.shoplite.ui.screen.HomeScreen

@Composable
fun Routes(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppPages.Home.route
    ) {
        composable(route = AppPages.Home.route) {
            HomeScreen()
        }


    }
}