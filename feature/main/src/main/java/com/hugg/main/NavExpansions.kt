package com.hugg.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.hugg.domain.model.enums.SurgeryType
import com.hugg.domain.model.request.sign.SignUpMaleRequestVo
import com.hugg.domain.model.request.sign.SignUpRequestVo
import com.hugg.sign.femaleSignUp.chooseSurgery.ChooseSurgeryContainer
import com.hugg.sign.femaleSignUp.spouseCodeFemale.SpouseCodeFemaleContainer
import com.hugg.sign.femaleSignUp.startSurgery.SurgeryStartContainer
import com.hugg.sign.femaleSignUp.surgeryCount.SurgeryCountContainer
import com.hugg.sign.inputSsn.InputSsnContainer
import com.hugg.sign.maleSignUp.MaleSignUpContainer
import com.hugg.sign.maleSignUp.MaleSignUpScreen
import com.hugg.sign.onboarding.OnboardingContainer

fun NavGraphBuilder.signNavGraph(navController: NavHostController) {
    navigation(startDestination = Routes.OnboardingScreen.route, route = Routes.SignGraph.route) {

        composable(Routes.OnboardingScreen.route) { OnboardingContainer(
            navigateInputSsn = { accessToken : String -> navController.navigate(route = Routes.InputSsnScreen.getRouteInputSsn(accessToken)) }
        ) }

        composable(
            route = Routes.InputSsnScreen.route,
            arguments = listOf(
                navArgument("accessToken") { type = NavType.StringType },
            )
        ) {
            val accessToken = it.arguments?.getString("accessToken") ?: ""
            InputSsnContainer(
                navigateFemaleSignUpPage = { ssn : String -> navController.navigate(route = Routes.FemaleSignUpChooseSurgery.getRouteChooseSurgery(accessToken, ssn)) },
                navigateMaleSignUpPage = { ssn : String -> navController.navigate(route = Routes.MaleSignUp.getRouteMaleSignUp(accessToken, ssn)) },
                goToBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.FemaleSignUpChooseSurgery.route,
            arguments = listOf(
                navArgument("accessToken") { type = NavType.StringType },
                navArgument("ssn") { type = NavType.StringType },
            )
        ) {
            val accessToken = it.arguments?.getString("accessToken") ?: ""
            val ssn = it.arguments?.getString("ssn") ?: ""
            ChooseSurgeryContainer(
                navigateSpouseCodePage = { type : String -> navController.navigate(Routes.FemaleSignUpSpouseCode.getRouteFemaleSpouseCode(accessToken, ssn, type, null, null))},
                navigateSurgeryCountPage = { type : String -> navController.navigate(Routes.FemaleSignUpSurgeryCount.getRouteSurgeryCount(accessToken, ssn, type)) },
                goToBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.FemaleSignUpSurgeryCount.route,
            arguments = listOf(
                navArgument("accessToken") { type = NavType.StringType },
                navArgument("ssn") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType },
            )
        ) {
            val accessToken = it.arguments?.getString("accessToken") ?: ""
            val ssn = it.arguments?.getString("ssn") ?: ""
            val type = it.arguments?.getString("type") ?: ""
            SurgeryCountContainer(
                navigateSurgeryStart = { count : Int -> navController.navigate(Routes.FemaleSignUpStartSurgery.getRouteSurgeryStart(accessToken, ssn, type, count))},
                goToBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.FemaleSignUpStartSurgery.route,
            arguments = listOf(
                navArgument("accessToken") { type = NavType.StringType },
                navArgument("ssn") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType },
                navArgument("count") { type = NavType.IntType },
            )
        ) {
            val accessToken = it.arguments?.getString("accessToken") ?: ""
            val ssn = it.arguments?.getString("ssn") ?: ""
            val type = it.arguments?.getString("type") ?: ""
            val count = it.arguments?.getInt("count") ?: 0
            SurgeryStartContainer(
                navigateFemaleSpouseCode = { date : String -> navController.navigate(Routes.FemaleSignUpSpouseCode.getRouteFemaleSpouseCode(accessToken, ssn, type, count, date)) },
                goToBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.FemaleSignUpSpouseCode.route,
            arguments = listOf(
                navArgument("accessToken") { type = NavType.StringType },
                navArgument("ssn") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType },
                navArgument("count") { type = NavType.IntType },
                navArgument("date") { type = NavType.StringType },
            )
        ) {
            val accessToken = it.arguments?.getString("accessToken") ?: ""
            val ssn = it.arguments?.getString("ssn") ?: ""
            val type = SurgeryType.valuesOf(it.arguments?.getString("type") ?: "")
            val count = it.arguments?.getInt("count") ?: 0
            val date = it.arguments?.getString("date") ?: ""
            SpouseCodeFemaleContainer(
                navigateGoToHome = {  },
                accessToken = accessToken,
                signUpRequestVo = SignUpRequestVo(
                    surgeryType = type,
                    count = if(type == SurgeryType.THINK_SURGERY) null else count,
                    startAt = if(type == SurgeryType.THINK_SURGERY) null else date,
                    spouseCode = "",
                    ssn = ssn,
                    fcmToken = ""
                ),
                goToBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.MaleSignUp.route,
            arguments = listOf(
                navArgument("accessToken") { type = NavType.StringType },
                navArgument("ssn") { type = NavType.StringType },
            )
        ) {
            val accessToken = it.arguments?.getString("accessToken") ?: ""
            val ssn = it.arguments?.getString("ssn") ?: ""
            MaleSignUpContainer(
                navigateGoToHome = {},
                accessToken = accessToken,
                signUpMaleRequestVo = SignUpMaleRequestVo(spouseCode = "", ssn = ssn, fcmToken = ""),
                goToBack = { navController.popBackStack() }
            )
        }
    }
}

//fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
//    navigation(startDestination = "home", route = "home_graph") {
//        composable("home") { HomeScreen(navController) }
//        composable("home_details") { HomeDetailsScreen(navController) }
//    }
//}

sealed class Routes(val route : String){

    //----------------SIGN_GRAPH----------------//
    data object SignGraph : Routes("sign_graph")
    data object OnboardingScreen : Routes("onboarding")
    data object InputSsnScreen : Routes("input_ssn/{accessToken}"){
        fun getRouteInputSsn(accessToken : String) : String = "input_ssn/$accessToken"
    }
    data object FemaleSignUpChooseSurgery : Routes("choose_surgery/{accessToken}/{ssn}"){
        fun getRouteChooseSurgery(accessToken : String, ssn : String) : String = "choose_surgery/$accessToken/$ssn"
    }
    data object FemaleSignUpSurgeryCount : Routes("surgery_count/{accessToken}/{ssn}/{type}"){
        fun getRouteSurgeryCount(accessToken : String, ssn : String, type : String) : String = "surgery_count/$accessToken/$ssn/$type"
    }
    data object FemaleSignUpStartSurgery : Routes("surgery_start/{accessToken}/{ssn}/{type}/{count}"){
        fun getRouteSurgeryStart(accessToken : String, ssn : String, type : String, count : Int) : String = "surgery_start/$accessToken/$ssn/$type/$count"
    }
    data object FemaleSignUpSpouseCode : Routes("female_spouse_code/{accessToken}/{ssn}/{type}/{count}/{date}"){
        fun getRouteFemaleSpouseCode(accessToken : String, ssn : String, type : String, count : Int?, date : String?) = "female_spouse_code/$accessToken/$ssn/$type/${count ?: -1}/${date ?: "null"}\""
    }
    data object MaleSignUp : Routes("male_sign_up/{accessToken}/{ssn}"){
        fun getRouteMaleSignUp(accessToken: String, ssn: String) = "male_sign_up/$accessToken/$ssn"
    }
}