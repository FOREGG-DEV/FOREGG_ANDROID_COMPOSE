package com.hugg.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.hugg.account.accountCreateOrEdit.AccountCreateOrEditContainer
import com.hugg.account.accountMain.AccountContainer
import com.hugg.account.subsidyCreateOrEdit.SubsidyCreateOrEditContainer
import com.hugg.account.subsidyList.SubsidyListContainer
import com.hugg.calendar.calendarMain.CalendarContainer
import com.hugg.calendar.scheduleCreateOrEdit.ScheduleCreateOrEditContainer
import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.enums.RecordType
import com.hugg.domain.model.enums.SurgeryType
import com.hugg.domain.model.request.sign.SignUpMaleRequestVo
import com.hugg.domain.model.request.sign.SignUpRequestVo
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.UserInfo
import com.hugg.sign.femaleSignUp.chooseSurgery.ChooseSurgeryContainer
import com.hugg.sign.femaleSignUp.spouseCodeFemale.SpouseCodeFemaleContainer
import com.hugg.sign.femaleSignUp.startSurgery.SurgeryStartContainer
import com.hugg.sign.femaleSignUp.surgeryCount.SurgeryCountContainer
import com.hugg.sign.inputSsn.InputSsnContainer
import com.hugg.sign.maleSignUp.MaleSignUpContainer
import com.hugg.sign.onboarding.OnboardingContainer

fun NavGraphBuilder.signNavGraph(navController: NavHostController) {
    navigation(startDestination = Routes.OnboardingScreen.route, route = Routes.SignGraph.route) {

        composable(Routes.OnboardingScreen.route) { OnboardingContainer(
            navigateInputSsn = { accessToken : String -> navController.navigate(route = Routes.InputSsnScreen.getRouteInputSsn(accessToken)) },
            navigateHome = { navController.navigate(route = Routes.CalendarGraph.route) } // 임시!
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
                navigateGoToHome = { navController.navigate(route = Routes.CalendarGraph.route) }, // 임시!
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
                navigateGoToHome = { navController.navigate(route = Routes.CalendarGraph.route) }, // 임시
                accessToken = accessToken,
                signUpMaleRequestVo = SignUpMaleRequestVo(spouseCode = "", ssn = ssn, fcmToken = ""),
                goToBack = { navController.popBackStack() }
            )
        }
    }
}

fun NavGraphBuilder.calendarGraph(navController: NavHostController) {
    navigation(startDestination = Routes.CalendarScreen.route, route = Routes.CalendarGraph.route) {

        composable(Routes.CalendarScreen.route) { CalendarContainer(
            navigateCreateSchedule = { pageType, recordType, id, day -> navController.navigate(Routes.CalendarScheduleCreateOrEdit.getRouteCalendarScheduleCreateOrEdit(pageType.type, recordType.type, id, day))}
        ) }

        composable(
            route = Routes.CalendarScheduleCreateOrEdit.route,
            arguments = listOf(
                navArgument("pageType") { type = NavType.StringType },
                navArgument("recordType") { type = NavType.StringType },
                navArgument("id") { type = NavType.LongType },
                navArgument("selectDate") { type = NavType.StringType },
            )
        ) {
            val pageType = CreateOrEditType.getEnumType(it.arguments?.getString("pageType") ?: "")
            val recordType = RecordType.getEnumType(it.arguments?.getString("recordType") ?: "")
            val selectDate = it.arguments?.getString("selectDate") ?: TimeFormatter.getToday()
            val id = it.arguments?.getLong("id") ?: -1

            ScheduleCreateOrEditContainer(
                goToBack = { navController.popBackStack() },
                pageType = pageType,
                recordType = recordType,
                selectDate = selectDate,
                id = id
            )
        }
    }
}

fun NavGraphBuilder.accountGraph(navController: NavHostController) {
    navigation(startDestination = Routes.AccountScreen.route, route = Routes.AccountGraph.route) {

        composable(Routes.AccountScreen.route) { AccountContainer(
            navigateToSubsidyList = { round -> navController.navigate(Routes.AccountSubsidyList.getRouteAccountSubsidyList(round)) },
            navigateToCreateOrEditAccount = { id, type -> navController.navigate(Routes.AccountCreateOrEdit.getRouteAccountCreateOrEdit(id, type.name))}
        ) }

        composable(
            route = Routes.AccountSubsidyList.route,
            arguments = listOf(
                navArgument("round") { type = NavType.IntType },
            )
        ) {
            val round = it.arguments?.getInt("round") ?: UserInfo.info.round
            SubsidyListContainer(
                onClickCreateEditSubsidyBtn = {id, type, round -> navController.navigate(Routes.AccountSubsidyCreateOrEdit.getRouteAccountSubsidyCreateOrEdit(id, type.name, round)) },
                nowRound = round,
                goToBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.AccountSubsidyCreateOrEdit.route,
            arguments = listOf(
                navArgument("id") { type = NavType.LongType },
                navArgument("round") { type = NavType.IntType },
                navArgument("type") { type = NavType.StringType}
            )
        ) {
            val id = it.arguments?.getLong("id") ?: -1
            val round = it.arguments?.getInt("round") ?: UserInfo.info.round
            val type = CreateOrEditType.getEnumType(it.arguments?.getString("type") ?: "")
            SubsidyCreateOrEditContainer(
                id = id,
                type = type,
                round = round,
                goToBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.AccountCreateOrEdit.route,
            arguments = listOf(
                navArgument("id") { type = NavType.LongType },
                navArgument("type") { type = NavType.StringType}
            )
        ) {
            val id = it.arguments?.getLong("id") ?: -1
            val type = CreateOrEditType.getEnumType(it.arguments?.getString("type") ?: "")
            AccountCreateOrEditContainer(
                navigateCreateSubsidy = { round -> navController.navigate(Routes.AccountSubsidyCreateOrEdit.getRouteAccountSubsidyCreateOrEdit(id, type.name, round))},
                id = id,
                type = type,
                goToBack = { navController.popBackStack() }
            )
        }
    }
}