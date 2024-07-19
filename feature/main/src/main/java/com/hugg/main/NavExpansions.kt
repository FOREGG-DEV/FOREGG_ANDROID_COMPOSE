package com.hugg.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hugg.sign.SignScreen

fun NavGraphBuilder.signNavGraph(navController: NavHostController) {
    navigation(startDestination = Routes.Sign.ONBOARDING, route = Routes.Sign.GRAPH) {
        composable(Routes.Sign.ONBOARDING) { SignScreen(navController) }
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