package com.giraffe.caffeineapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.giraffe.caffeineapp.screen.coffeetype.CoffeeTypeScreen
import com.giraffe.caffeineapp.screen.start.StartScreen

enum class Destination(
    val route: String,
) {
    START("start"),
    COFFEE_TYPE("coffeetype"),
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Destination = Destination.START,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.START -> StartScreen {
                        navController.navigateToCoffeeTypeScreen()
                    }

                    Destination.COFFEE_TYPE -> CoffeeTypeScreen()
                }
            }
        }
    }
}

fun NavHostController.navigateToCoffeeTypeScreen() = navigate(Destination.COFFEE_TYPE.route)