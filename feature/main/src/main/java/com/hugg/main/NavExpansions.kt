package com.hugg.main

import android.net.Uri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.hugg.dailyhugg.create.CreateDailyHuggScreen
import com.hugg.dailyhugg.create.complete.DailyHuggCreationSuccessScreen
import com.hugg.dailyhugg.preview.ImagePreviewScreen
import com.hugg.dailyhugg.main.DailyHuggScreen
import com.hugg.account.accountCreateOrEdit.AccountCreateOrEditContainer
import com.hugg.account.accountMain.AccountContainer
import com.hugg.account.subsidyCreateOrEdit.SubsidyCreateOrEditContainer
import com.hugg.account.subsidyList.SubsidyListContainer
import com.hugg.calendar.calendarMain.CalendarContainer
import com.hugg.calendar.scheduleCreateOrEdit.ScheduleCreateOrEditContainer
import com.hugg.challenge.main.ChallengeMainScreen
import com.hugg.create.CreateChallengeScreen
import com.hugg.dailyhugg.all.DailyHuggListScreen
import com.hugg.dailyhugg.create.CreateEditDailyHuggPageState
import com.hugg.dailyhugg.edit.EditDailyHuggScreen
import com.hugg.dailyhugg.reply.ReplyDailyHuggScreen
import com.hugg.dailyhugg.reply.complete.DailyHuggReplySuccessContent
import com.hugg.dailyhugg.reply.complete.DailyHuggReplySuccessScreen
import com.hugg.domain.model.enums.ChallengeTabType
import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.enums.RecordType
import com.hugg.domain.model.enums.SurgeryType
import com.hugg.domain.model.request.sign.SignUpMaleRequestVo
import com.hugg.domain.model.request.sign.SignUpRequestVo
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.UserInfo
import com.hugg.home.homeMain.HomeContainer
import com.hugg.list.ChallengeListScreen
import com.hugg.home.notification.NotificationContainer
import com.hugg.list.detail.ChallengeDetailScreen
import com.hugg.mypage.cs.MyPageCsContainer
import com.hugg.mypage.main.MyPageContainer
import com.hugg.mypage.myMedicineInjection.MyPageMedInjContainer
import com.hugg.mypage.profileManagement.MyPageProfileManagementContainer
import com.hugg.mypage.spouse.MyPageSpouseContainer
import com.hugg.notification.InjMedDetailContainer
import com.hugg.sign.accessPermission.AccessPermissionContainer
import com.hugg.sign.femaleSignUp.batteryModal.BatteryOptimizationContainer
import com.hugg.sign.femaleSignUp.chooseSurgery.ChooseSurgeryContainer
import com.hugg.sign.femaleSignUp.spouseCodeFemale.SpouseCodeFemaleContainer
import com.hugg.sign.femaleSignUp.startSurgery.SurgeryStartContainer
import com.hugg.sign.femaleSignUp.surgeryCount.SurgeryCountContainer
import com.hugg.sign.inputSsn.InputSsnContainer
import com.hugg.sign.maleSignUp.MaleSignUpContainer
import com.hugg.sign.onboarding.OnboardingContainer
import com.hugg.sign.serviceTerms.ServiceTermsContainer
import com.hugg.sign.splash.HuggSplashContainer
import com.hugg.support.ChallengeSupportScreen

fun NavGraphBuilder.signNavGraph(navController: NavHostController, initialNavigation : String) {
    navigation(startDestination = Routes.SplashScreen.route, route = Routes.SignGraph.route) {

        composable(Routes.SplashScreen.route) { HuggSplashContainer(
            navigateToOnboarding = { navController.navigate(route = Routes.OnboardingScreen.route) {
                popUpTo(navController.graph.id) { inclusive = true }
                launchSingleTop = true
            } },
            navigateToHome = { navController.navigate(route = Routes.HomeGraph.route) {
                popUpTo(navController.graph.id) { inclusive = true }
                launchSingleTop = true }
                if(initialNavigation.isNotEmpty()) navController.navigate(initialNavigation)
            }
        ) }

        composable(Routes.OnboardingScreen.route) { OnboardingContainer(
            navigateServiceTerms = { accessToken : String -> navController.navigate(route = Routes.ServiceTermsScreen.getRouteServiceTerms(accessToken)) },
            navigateHome = { navController.navigate(route = Routes.HomeGraph.route) {
                    popUpTo(navController.graph.id) { inclusive = true }
                    launchSingleTop = true }
                if(initialNavigation.isNotEmpty()) navController.navigate(initialNavigation)
            }
        ) }

        composable(
            route = Routes.ServiceTermsScreen.route,
            arguments = listOf(
                navArgument("accessToken") { type = NavType.StringType },
            )
        ) {
            val accessToken = it.arguments?.getString("accessToken") ?: ""
            ServiceTermsContainer(
                navigateAccessPermission = { navController.navigate(route = Routes.AccessPermissionScreen.getRouteAccessPermission(accessToken)) },
                goToBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.AccessPermissionScreen.route,
            arguments = listOf(
                navArgument("accessToken") { type = NavType.StringType },
            )
        ) {
            val accessToken = it.arguments?.getString("accessToken") ?: ""
            AccessPermissionContainer(
                navigateInputSsn = { navController.navigate(route = Routes.InputSsnScreen.getRouteInputSsn(accessToken)) },
                goToBack = { navController.popBackStack() }
            )
        }

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
                navigationGoToBatteryOptimization = {
                    navController.navigate(route = Routes.SignUpBatteryOptimization.route)
                },
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
            route = Routes.SignUpBatteryOptimization.route
        ) {
            BatteryOptimizationContainer(
                navigateGoToHome = {
                    navController.navigate(route = Routes.HomeGraph.route) {
                        popUpTo(navController.graph.id) { inclusive = true }
                        launchSingleTop = true
                    }
                    if(initialNavigation.isNotEmpty()) navController.navigate(initialNavigation)
                },
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
                navigateGoToHome = { navController.navigate(route = Routes.HomeGraph.route) {
                    popUpTo(navController.graph.id) { inclusive = true }
                    launchSingleTop = true }
                    if(initialNavigation.isNotEmpty()) navController.navigate(initialNavigation)
                },
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
            navigateToCreateOrEditAccount = { id, type -> navController.navigate(Routes.AccountCreateOrEdit.getRouteAccountCreateOrEdit(id, type.type))}
        ) }

        composable(
            route = Routes.AccountSubsidyList.route,
            arguments = listOf(
                navArgument("round") { type = NavType.IntType },
            )
        ) {
            val round = it.arguments?.getInt("round") ?: UserInfo.info.round
            SubsidyListContainer(
                onClickCreateEditSubsidyBtn = {id, type, round -> navController.navigate(Routes.AccountSubsidyCreateOrEdit.getRouteAccountSubsidyCreateOrEdit(id, type.type, round)) },
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
                navigateCreateSubsidy = { round -> navController.navigate(Routes.AccountSubsidyCreateOrEdit.getRouteAccountSubsidyCreateOrEdit(id, CreateOrEditType.CREATE.type, round))},
                id = id,
                type = type,
                goToBack = { navController.popBackStack() }
            )
        }
    }
}

fun NavGraphBuilder.dailyHuggGraph(navController: NavHostController) {
    val popScreen = { croppedUri: Uri? ->
        navController.previousBackStackEntry?.savedStateHandle?.set("croppedUri", croppedUri)
        navController.popBackStack()
    }

    navigation(startDestination = Routes.DailyHuggScreen.route, route = Routes.DailyHuggGraph.route) {
        composable(
            route = Routes.DailyHuggScreen.route,
            arguments = listOf(
                navArgument("date") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date") ?: ""

            DailyHuggScreen(
                onClickCreateDailyHugg = { navController.navigate(Routes.CreateDailyHuggScreen.route) },
                onClickDailyHuggItem = { dailyHuggPageState: CreateEditDailyHuggPageState, id: Long ->
                    navController.navigate(Routes.EditDailyHuggScreen.createRoute(id)) {
                        navController.currentBackStackEntry?.savedStateHandle?.set("dailyHuggPageState", dailyHuggPageState)
                    }
                },
                goToDailyHuggList = { navController.navigate(Routes.DailyHuggListScreen.route) },
                goToReplyPage = { date -> navController.navigate(Routes.ReplyDailyHuggScreen.createRoute(date))},
                popScreen = { navController.popBackStack() },
                selectedDate = date
            )
        }

        composable(Routes.CreateDailyHuggScreen.route) {
            CreateDailyHuggScreen(
                goToImgPreview = { uri: Uri? ->
                    uri?.let {
                        navController.navigate(Routes.ImagePreviewScreen.createRoute(uri))
                    }
                },
                getSavedUri = { navController.currentBackStackEntry?.savedStateHandle?.get<Uri>("croppedUri") },
                goToDailyHuggCreationSuccessScreen = { navController.navigate(Routes.DailyHuggCreationSuccessScreen.route) },
                popScreen = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.ReplyDailyHuggScreen.route,
            arguments = listOf(
                navArgument("date") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date") ?: ""

            ReplyDailyHuggScreen(
                goToSuccessReplyPage = { navController.navigate(Routes.DailyHuggReplySuccessScreen.createRoute(date)) },
                popScreen = { navController.popBackStack() },
                selectedDate = date
            )
        }

        composable(
            route = Routes.DailyHuggReplySuccessScreen.route,
            arguments = listOf(
                navArgument("date") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date") ?: ""

            DailyHuggReplySuccessScreen(
                goToDailyHuggMain = {
                    navController.navigate(Routes.DailyHuggScreen.createRoute(date)) {
                        popUpTo(Routes.DailyHuggGraph.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Routes.ImagePreviewScreen.route,
            arguments = listOf(
                navArgument("uri") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val uriString = backStackEntry.arguments?.getString("uri")
            val uri = uriString?.let { Uri.parse(Uri.decode(it)) }
            ImagePreviewScreen(
                selectedUri = uri,
                popScreen =  { croppedUri: Uri? ->
                    popScreen(croppedUri)
                }
            )
        }

        composable(Routes.DailyHuggCreationSuccessScreen.route) {
            DailyHuggCreationSuccessScreen(
                goToDailyHuggMain = {
                    navController.navigate(Routes.DailyHuggScreen.createRoute(TimeFormatter.getToday())) {
                        popUpTo(Routes.DailyHuggGraph.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Routes.EditDailyHuggScreen.route,
            arguments = listOf(
                navArgument("id") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val dailyHuggPageState = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<CreateEditDailyHuggPageState>("dailyHuggPageState")
            val id = backStackEntry.arguments?.getLong("id")

            EditDailyHuggScreen(
                getSavedUri = { navController.currentBackStackEntry?.savedStateHandle?.get<Uri>("croppedUri") },
                dailyHuggPageState = dailyHuggPageState,
                popScreen = { navController.popBackStack() },
                goToImgPreview = { uri: Uri? ->
                    uri?.let {
                        navController.navigate(Routes.ImagePreviewScreen.createRoute(uri))
                    }
                },
                id = id
            )
        }

        composable(Routes.DailyHuggListScreen.route) {
            DailyHuggListScreen(
                popScreen = { navController.popBackStack() },
                goToDailyHuggDetail = { date: String ->
                    navController.navigate(Routes.DailyHuggScreen.createRoute(date))
                }
            )
        }
    }
}

fun NavGraphBuilder.challengeGraph(navController: NavHostController) {
    navigation(startDestination = Routes.ChallengeScreen.route, route = Routes.ChallengeGraph.route) {
        composable(Routes.ChallengeScreen.route) {
            ChallengeMainScreen(
                popScreen = { navController.popBackStack() },
                challengeTabType = ChallengeTabType.COMMON,
                goToChallengeList = { navController.navigate(Routes.ChallengeListScreen.route) },
                goToCreateChallenge = { navController.navigate(Routes.CreateChallengeScreen.route) },
                goToChallengeSupport = { navController.navigate(Routes.ChallengeSupportScreen.createRoute(it)) }
            )
        }

        composable(Routes.MyChallengeScreen.route) {
            ChallengeMainScreen(
                popScreen = { navController.popBackStack() },
                challengeTabType = ChallengeTabType.MY,
                goToChallengeList = { navController.navigate(Routes.ChallengeListScreen.route) },
                goToCreateChallenge = { navController.navigate(Routes.CreateChallengeScreen.route) },
                goToChallengeSupport = { navController.navigate(Routes.ChallengeSupportScreen.createRoute(it)) }
            )
        }

        composable(Routes.ChallengeListScreen.route) {
            ChallengeListScreen(
                popScreen = { navController.popBackStack() },
                goToCreateChallenge = { navController.navigate(Routes.CreateChallengeScreen.route) },
                goToChallengeDetail = { navController.navigate(Routes.ChallengeDetailScreen.createRoute(it)) }
            )
        }

        composable(Routes.CreateChallengeScreen.route) {
            CreateChallengeScreen (
                popScreen = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.ChallengeSupportScreen.route,
            arguments = listOf(
                navArgument("challengeId") { type = NavType.LongType }
            )
        ) { backstackEntry ->
            val id = backstackEntry.arguments?.getLong("challengeId")
            ChallengeSupportScreen(
                popScreen = { navController.popBackStack() },
                challengeId = id
            )
        }

        composable(
            route = Routes.ChallengeDetailScreen.route,
            arguments = listOf(
                navArgument("challengeId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("challengeId")

            id?.let {
                ChallengeDetailScreen(
                    popScreen = { navController.popBackStack() },
                    challengeId = it
                )
            }
        }
    }
}

fun NavGraphBuilder.myPageGraph(navController: NavHostController) {
    navigation(startDestination = Routes.MyPageScreen.route, route = Routes.MyPageGraph.route) {

        composable(Routes.MyPageScreen.route) {
            MyPageContainer(
                navigateGoToRegistration = { navController.navigate(Routes.MyPageProfileManagementScreen.route) },
                navigateGoToCs = { navController.navigate(Routes.MyPageCsScreen.route) },
                navigateGoToMyMedInj = { navController.navigate(Routes.MyPageMedInjScreen.route) },
                navigateGoToSpouse = { navController.navigate(Routes.MyPageSpouseScreen.route) },
            )
        }

        composable(Routes.MyPageSpouseScreen.route) {
            MyPageSpouseContainer(
                goToBack = { navController.popBackStack() },
            )
        }

        composable(Routes.MyPageMedInjScreen.route) {
            MyPageMedInjContainer(
                goToBack = { navController.popBackStack() },
                navigateDetail = { recordType, id -> navController.navigate(Routes.CalendarScheduleCreateOrEdit.getRouteCalendarScheduleCreateOrEdit(CreateOrEditType.EDIT.type, recordType.type, id, TimeFormatter.getToday()))}
            )
        }

        composable(Routes.MyPageCsScreen.route) {
            MyPageCsContainer(
                goToBack = { navController.popBackStack() },
            )
        }

        composable(Routes.MyPageProfileManagementScreen.route) {
            MyPageProfileManagementContainer(
                goToBack = { navController.popBackStack() },
                navigateToSignGraph = { navController.navigate(route = Routes.SignGraph.route) {
                        popUpTo(navController.graph.id) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation(startDestination = Routes.HomeScreen.route, route = Routes.HomeGraph.route) {

        composable(Routes.HomeScreen.route) {
            HomeContainer(
                navigateGoToCalendarDetail = { id, type -> navController.navigate(Routes.CalendarScheduleCreateOrEdit.getRouteCalendarScheduleCreateOrEdit(CreateOrEditType.EDIT.type, type.type, id, TimeFormatter.getToday()))},
                navigateGoToChallenge = { navController.navigate(Routes.ChallengeGraph.route) },
                navigateGoToDailyHugg = { navController.navigate(Routes.DailyHuggGraph.route)},
                navigateGoToNotification = { navController.navigate(Routes.NotificationScreen.route) },
                navigateGoToDailyHuggDetail = { date -> navController.navigate(Routes.DailyHuggScreen.createRoute(date)) }
            )
        }

        composable(Routes.NotificationScreen.route) {
            NotificationContainer(
                navigateGoToChallengeCheer = { id -> navController.navigate(Routes.ChallengeSupportScreen.createRoute(id))},
                navigateGoToDailyHuggDetail = { date -> navController.navigate(Routes.DailyHuggScreen.createRoute(date)) },
                goToBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.InjMedInfoScreen.route,
            arguments = listOf(
                navArgument("type") { type = NavType.StringType},
                navArgument("id") { type = NavType.LongType },
                navArgument("date") { type = NavType.StringType },
                navArgument("time") { type = NavType.StringType}
            )
        ) {
            val id = it.arguments?.getLong("id") ?: -1
            val type = RecordType.getEnumTypeByString(it.arguments?.getString("type") ?: "")
            val time = it.arguments?.getString("time") ?: ""
            val date = it.arguments?.getString("date") ?: ""

            InjMedDetailContainer(
                goToBack = { navController.popBackStack() },
                type = type,
                id = id,
                time = time,
                date = date
            )
        }
    }
}