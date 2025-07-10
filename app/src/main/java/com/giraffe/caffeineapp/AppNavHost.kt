package com.giraffe.caffeineapp

import android.net.Uri
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
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

sealed class Destination(
    val route: String,
) {
    object Start : Destination("start")
    object CoffeeType : Destination("coffee_type")
    object CupSize : Destination("cup_size/{coffee_name}") {
        const val ARG_COFFEE_NAME = "coffee_name"
        fun route(coffeeName: String): String {
            return "cup_size/${Uri.encode(coffeeName)}"
        }
    }

    object Ready : Destination("ready")
    object Snack : Destination("snack")
    object ThankYou : Destination("thank_you/{snack_name}/{snack_image}") {
        const val ARG_SNACK_NAME = "snack_name"
        const val ARG_SNACK_IMAGE = "snack_image"
        fun route(snackName: String, snackImage: Int): String {
            return "thank_you/${Uri.encode(snackName)}/$snackImage"
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Destination = Destination.Start,
) {
    SharedTransitionLayout(
        modifier = modifier,
    ) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination.route
        ) {
            composable(route = Destination.Start.route) {
                StartScreen(navigateToCoffeeTypeScreen = navController::navigateToCoffeeTypeScreen)
            }
            composable(route = Destination.CoffeeType.route) {
                CoffeeTypeScreen(
                    animatedVisibilityScope = this,
                    navigateToCupSizeScreen = navController::navigateToCupSizeScreen
                )
            }
            composable(
                route = Destination.CupSize.route,
                arguments = listOf(navArgument(Destination.CupSize.ARG_COFFEE_NAME) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                CupSizeScreen(
                    animatedVisibilityScope = this,
                    coffeeName = backStackEntry.getStringArg(Destination.CupSize.ARG_COFFEE_NAME),
                    onBackClick = navController::popBackStack,
                    navigateToReadyScreen = navController::navigateToReadyScreen
                )
            }
            composable(route = Destination.Ready.route) {
                ReadyScreen(
                    animatedVisibilityScope = this,
                    navigateToSnackScreen = navController::navigateToSnackScreen,
                    onCloseClick = navController::navigateToStartScreen
                )
            }
            composable(route = Destination.Snack.route) {
                SnackScreen(
                    animatedVisibilityScope = this,
                    navigateToThankYouScreen = navController::navigateToThankYouScreen,
                    onCloseClick = navController::navigateToStartScreen
                )
            }
            composable(
                route = Destination.ThankYou.route,
                arguments = listOf(
                    navArgument(Destination.ThankYou.ARG_SNACK_NAME) { type = NavType.StringType },
                    navArgument(Destination.ThankYou.ARG_SNACK_IMAGE) { type = NavType.IntType })
            ) { backStackEntry ->
                ThankYouScreen(
                    animatedVisibilityScope = this,
                    snack = Snack(
                        name = backStackEntry.getStringArg(Destination.ThankYou.ARG_SNACK_NAME),
                        image = backStackEntry.getIntArg(Destination.ThankYou.ARG_SNACK_IMAGE)
                    ),
                    navigateToStartScreen = navController::navigateToStartScreen
                )
            }
        }
    }
}

fun NavHostController.navigateToStartScreen() = navigate(Destination.Start.route)
fun NavHostController.navigateToCoffeeTypeScreen() = navigate(Destination.CoffeeType.route)
fun NavHostController.navigateToCupSizeScreen(coffeeName: String) =
    navigate(Destination.CupSize.route(coffeeName))

fun NavHostController.navigateToReadyScreen() = navigate(Destination.Ready.route)
fun NavHostController.navigateToSnackScreen() = navigate(Destination.Snack.route)
fun NavHostController.navigateToThankYouScreen(snack: Snack) =
    navigate(Destination.ThankYou.route(snack.name, snack.image))

fun NavBackStackEntry.getStringArg(key: String): String =
    arguments?.getString(key) ?: error("Missing argument: $key")

fun NavBackStackEntry.getIntArg(key: String): Int =
    arguments?.getInt(key) ?: error("Missing argument: $key")
