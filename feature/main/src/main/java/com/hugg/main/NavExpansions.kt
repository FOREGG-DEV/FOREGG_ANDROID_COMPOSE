package com.hugg.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hugg.sign.onboarding.OnboardingContainer
import com.hugg.sign.onboarding.OnboardingScreen

fun NavGraphBuilder.signNavGraph(navController: NavHostController) {
    navigation(startDestination = Routes.Sign.ONBOARDING, route = Routes.Sign.GRAPH) {
        composable(Routes.Sign.ONBOARDING) { OnboardingContainer() }
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
    }
}