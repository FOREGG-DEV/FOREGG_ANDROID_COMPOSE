package com.hugg.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.hugg.sign.inputSsn.InputSsnContainer
import com.hugg.sign.onboarding.OnboardingContainer

fun NavGraphBuilder.signNavGraph(navController: NavHostController) {
    navigation(startDestination = Routes.Sign.ONBOARDING, route = Routes.Sign.GRAPH) {
        composable(Routes.Sign.ONBOARDING) { OnboardingContainer(
            navigateInputSsn = { accessToken -> navController.navigate(route = Routes.Sign.getRouteInputSsn(accessToken)) }
        ) }
        composable(
            route = Routes.Sign.INPUT_SSN,
            arguments = listOf(
                navArgument("accessToken") { type = NavType.StringType },
            )
        ) {
            InputSsnContainer(accessToken = it.arguments?.getString("accessToken") ?: "")
        }
    }
}

//fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
//    navigation(startDestination = "home", route = "home_graph") {
//        composable("home") { HomeScreen(navController) }
//        composable("home_details") { HomeDetailsScreen(navController) }
//    }
//}

object Routes {
    object Sign {
        const val GRAPH = "sign_graph"
        const val ONBOARDING = "onboarding"
        const val INPUT_SSN = "input_ssn/{accessToken}"

        fun getRouteInputSsn(accessToken : String) = "input_ssn/$accessToken"
    }
}