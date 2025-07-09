package com.giraffe.caffeineapp

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.giraffe.caffeineapp.screen.ThankYouScreen
import com.giraffe.caffeineapp.screen.coffeetype.CoffeeTypeScreen
import com.giraffe.caffeineapp.screen.cupsize.CupSizeScreen
import com.giraffe.caffeineapp.screen.ready.ReadyScreen
import com.giraffe.caffeineapp.screen.snack.Snack
import com.giraffe.caffeineapp.screen.snack.SnackScreen
import com.giraffe.caffeineapp.screen.start.StartScreen

enum class Destination(
    val route: String,
) {
    START("start"),
    COFFEE_TYPE("coffee type"),
    CUP_SIZE("cup size"),
    READY("ready"),
    SNACK("snack"),
    THANK_YOU("thank_you/{snack_name}/{snack_image}"),
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

        composable(Destination.START.route) {
            StartScreen(navController::navigateToCoffeeTypeScreen)
        }
        composable(Destination.COFFEE_TYPE.route) {
            CoffeeTypeScreen(navController::navigateToCupSizeScreen)
        }
        composable(Destination.CUP_SIZE.route) {
            CupSizeScreen(
                onBackClick = navController::popBackStack,
                navigateToReadyScreen = navController::navigateToReadyScreen
            )
        }
        composable(Destination.READY.route) {
            ReadyScreen(navController::navigateToSnackScreen)
        }
        composable(Destination.SNACK.route) {
            SnackScreen(navController::navigateToThankYouScreen)
        }
        composable(
            Destination.THANK_YOU.route, arguments = listOf(
                navArgument("snack_name") { type = NavType.StringType },
                navArgument("snack_image") { type = NavType.IntType })
        ) { backStackEntry ->
            val snackName =
                backStackEntry.arguments?.getString("snack_name") ?: "Unknown"
            val snackImage = backStackEntry.arguments?.getInt("snack_image") ?: 0
            ThankYouScreen(Snack(snackName, snackImage))
        }
    }
}

fun NavHostController.navigateToCoffeeTypeScreen() = navigate(Destination.COFFEE_TYPE.route)
fun NavHostController.navigateToCupSizeScreen() = navigate(Destination.CUP_SIZE.route)
fun NavHostController.navigateToReadyScreen() = navigate(Destination.READY.route)
fun NavHostController.navigateToSnackScreen() = navigate(Destination.SNACK.route)
fun NavHostController.navigateToThankYouScreen(snack: Snack) {
    val encodedName = Uri.encode(snack.name) // encode special chars & spaces
    navigate("thank_you/$encodedName/${snack.image}")
}