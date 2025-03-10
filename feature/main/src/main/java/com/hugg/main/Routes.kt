package com.hugg.main

import android.net.Uri
import okhttp3.Route

sealed class Routes(val route : String){

    //----------------SIGN_GRAPH----------------//
    data object SignGraph : Routes("sign_graph")
    data object SplashScreen : Routes("splash")
    data object OnboardingScreen : Routes("onboarding")
    data object ServiceTermsScreen : Routes("service_terms/{accessToken}") {
        fun getRouteServiceTerms(accessToken : String) : String = "service_terms/$accessToken"
    }

    data object AccessPermissionScreen : Routes("access_permission/{accessToken}"){
        fun getRouteAccessPermission(accessToken : String) : String = "access_permission/$accessToken"
    }
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

    data object SignUpBatteryOptimization : Routes("battery_optimization")
    data object MaleSignUp : Routes("male_sign_up/{accessToken}/{ssn}"){
        fun getRouteMaleSignUp(accessToken: String, ssn: String) = "male_sign_up/$accessToken/$ssn"
    }

    //----------------CALENDAR_GRAPH----------------//
    data object CalendarGraph : Routes("calendar_graph")
    data object CalendarScreen : Routes("calendar")
    data object CalendarScheduleCreateOrEdit : Routes("calendarScheduleCreateOrEdit/{pageType}/{recordType}/{id}/{selectDate}"){
        fun getRouteCalendarScheduleCreateOrEdit(pageType: String, recordType : String, id : Long, selectDate : String) = "calendarScheduleCreateOrEdit/$pageType/$recordType/$id/$selectDate"
    }

    //----------------ACCOUNT_GRAPH----------------//
    data object AccountGraph : Routes("account_graph")
    data object AccountScreen : Routes("account")
    data object AccountSubsidyList : Routes("accountSubsidyList/{round}"){
        fun getRouteAccountSubsidyList(round: Int) = "accountSubsidyList/$round"
    }
    data object AccountSubsidyCreateOrEdit : Routes("accountSubsidyCreateOrEdit/{id}/{type}/{round}"){
        fun getRouteAccountSubsidyCreateOrEdit(id: Long, type : String, round : Int) = "accountSubsidyCreateOrEdit/$id/$type/$round"
    }

    data object AccountCreateOrEdit : Routes("accountCreateOrEdit/{id}/{type}"){
        fun getRouteAccountCreateOrEdit(id: Long, type : String) = "accountCreateOrEdit/$id/$type"
    }

    // DAILYHUGG_GRAPH
    data object DailyHuggGraph : Routes("daily_hugg_graph")
    data object DailyHuggScreen : Routes("daily_hugg/{date}") {
        fun createRoute(date: String) = "daily_hugg/$date"
    }
    data object CreateDailyHuggScreen : Routes("create_daily_hugg")
    data object ReplyDailyHuggScreen : Routes("reply_daily_hugg/{date}"){
        fun createRoute(date: String) = "reply_daily_hugg/$date"
    }
    data object ImagePreviewScreen: Routes("image_preview/{uri}") {
        fun createRoute(uri: Uri) = "image_preview/${Uri.encode(uri.toString())}"
    }
    data object DailyHuggCreationSuccessScreen: Routes("daily_hugg_creation_success")
    data object DailyHuggReplySuccessScreen: Routes("daily_hugg_reply_success/{date}"){
        fun createRoute(date : String) = "daily_hugg_reply_success/$date"
    }
    data object EditDailyHuggScreen: Routes("edit_daily_hugg/{id}") {
        fun createRoute(id: Long) = "edit_daily_hugg/$id"
    }
    data object DailyHuggListScreen: Routes("daily_hugg_list")

    // Challenge_Graph
    data object ChallengeGraph: Routes("challenge_graph")
    data object ChallengeScreen: Routes("challenge")
    data object MyChallengeScreen: Routes("myChallenge")
    data object ChallengeListScreen: Routes("challenge_list")
    data object CreateChallengeScreen: Routes("create_challenge")
    data object ChallengeSupportScreen: Routes("challenge_support/{challengeId}") {
        fun createRoute(id: Long) = "challenge_support/$id"
    }
    data object ChallengeDetailScreen: Routes("challenge_detail/{challengeId}") {
        fun createRoute(id: Long) = "challenge_detail/$id"
    }

    //----------------MY_PAGE_GRAPH----------------//
    data object MyPageGraph : Routes("my_page_graph")
    data object MyPageScreen : Routes("my_page")
    data object MyPageSpouseScreen : Routes("my_page_spouse")
    data object MyPageMedInjScreen : Routes("my_page_medicine_injection")
    data object MyPageCsScreen : Routes("my_page_customer_service")
    data object MyPageProfileManagementScreen : Routes("my_page_profile_management")

    //----------------HOME_GRAPH----------------//
    data object HomeGraph : Routes("home_graph")
    data object HomeScreen : Routes("home")
    data object NotificationScreen : Routes("notification_history")
    data object InjMedInfoScreen : Routes("inj_med_info_screen/{type}/{id}/{date}/{time}") {
        fun createRoute(type: String, id: Long, date: String, time: String) = "inj_med_info_screen/$type/$id/$date/$time"
    }
}