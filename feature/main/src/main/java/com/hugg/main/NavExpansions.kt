package com.hugg.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.hugg.domain.model.enums.SurgeryType
import com.hugg.domain.model.request.sign.SignUpRequestVo
import com.hugg.sign.femaleSignUp.chooseSurgery.ChooseSurgeryContainer
import com.hugg.sign.femaleSignUp.spouseCodeFemale.SpouseCodeFemaleContainer
import com.hugg.sign.femaleSignUp.startSurgery.SurgeryStartContainer
import com.hugg.sign.femaleSignUp.surgeryCount.SurgeryCountContainer
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
            val accessToken = it.arguments?.getString("accessToken") ?: ""
            InputSsnContainer(
                navigateFemaleSignUpPage = { ssn -> navController.navigate(route = Routes.Sign.getRouteChooseSurgery(accessToken, ssn)) },
                navigateMaleSignUpPage = {},
                goToBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.Sign.FEMALE_SIGN_UP_CHOOSE_SURGERY,
            arguments = listOf(
                navArgument("accessToken") { type = NavType.StringType },
                navArgument("ssn") { type = NavType.StringType },
            )
        ) {
            val accessToken = it.arguments?.getString("accessToken") ?: ""
            val ssn = it.arguments?.getString("ssn") ?: ""
            ChooseSurgeryContainer(
                navigateSpouseCodePage = { type -> navController.navigate(Routes.Sign.getRouteFemaleSpouseCode(accessToken, ssn, type, null, null))},
                navigateSurgeryCountPage = { type -> navController.navigate(Routes.Sign.getRouteSurgeryCount(accessToken, ssn, type)) },
                goToBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.Sign.FEMALE_SIGN_UP_SURGERY_COUNT,
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
                navigateSurgeryStart = { count -> navController.navigate(Routes.Sign.getRouteSurgeryStart(accessToken, ssn, type, count))},
                goToBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.Sign.FEMALE_SIGN_UP_SURGERY_START,
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
                navigateFemaleSpouseCode = { date -> navController.navigate(Routes.Sign.getRouteFemaleSpouseCode(accessToken, ssn, type, count, date)) },
                goToBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.Sign.FEMALE_SIGN_UP_SPOUSE_CODE,
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
        const val FEMALE_SIGN_UP_CHOOSE_SURGERY = "choose_surgery/{accessToken}/{ssn}"
        const val FEMALE_SIGN_UP_SURGERY_COUNT = "surgery_count/{accessToken}/{ssn}/{type}"
        const val FEMALE_SIGN_UP_SURGERY_START = "surgery_start/{accessToken}/{ssn}/{type}/{count}"
        const val FEMALE_SIGN_UP_SPOUSE_CODE = "female_spouse_code/{accessToken}/{ssn}/{type}/{count}/{date}"

        fun getRouteInputSsn(accessToken : String) = "input_ssn/$accessToken"
        fun getRouteChooseSurgery(accessToken : String, ssn : String) = "choose_surgery/$accessToken/$ssn"
        fun getRouteSurgeryCount(accessToken : String, ssn : String, type : String) = "surgery_count/$accessToken/$ssn/$type"
        fun getRouteSurgeryStart(accessToken : String, ssn : String, type : String, count : Int) = "surgery_start/$accessToken/$ssn/$type/$count"
        fun getRouteFemaleSpouseCode(accessToken : String, ssn : String, type : String, count : Int?, date : String?) = "female_spouse_code/$accessToken/$ssn/$type/${count ?: -1}/${date ?: "null"}\""
    }
}