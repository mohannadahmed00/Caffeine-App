package com.giraffe.caffeineapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.giraffe.caffeineapp.screen.coffeetype.CoffeeTypeScreen
import com.giraffe.caffeineapp.screen.cupsize.CupSizeScreen
import com.giraffe.caffeineapp.screen.start.StartScreen

enum class Destination(
    val route: String,
) {
    START("start"),
    COFFEE_TYPE("coffee type"),
    CUP_SIZE("cup size"),
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

                    Destination.COFFEE_TYPE -> CoffeeTypeScreen {
                        navController.navigateToCupSizeScreen()
                    }

                    Destination.CUP_SIZE -> CupSizeScreen {
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}

fun NavHostController.navigateToCoffeeTypeScreen() = navigate(Destination.COFFEE_TYPE.route)
fun NavHostController.navigateToCupSizeScreen() = navigate(Destination.CUP_SIZE.route)