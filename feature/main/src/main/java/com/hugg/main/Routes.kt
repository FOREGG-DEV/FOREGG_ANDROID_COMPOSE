package com.hugg.main

import android.net.Uri

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
    data object DailyHuggScreen : Routes("daily_hugg")
    data object CreateDailyHuggScreen : Routes("create_daily_hugg")
    data object ImagePreviewScreen: Routes("image_preview/{uri}") {
        fun createRoute(uri: Uri) = "image_preview/${Uri.encode(uri.toString())}"
    }
    data object DailyHuggCreationSuccessScreen: Routes("daily_hugg_creation_success")
    data object EditDailyHuggScreen: Routes("edit_daily_hugg/{id}") {
        fun createRoute(id: Long) = "edit_daily_hugg/$id"
    }

    //----------------MY_PAGE_GRAPH----------------//
    data object MyPageGraph : Routes("my_page_graph")
    data object MyPageScreen : Routes("my_page")
    data object MyPageSpouseScreen : Routes("my_page_spouse")
    data object MyPageMedInjScreen : Routes("my_page_medicine_injection")
    data object MyPageCsScreen : Routes("my_page_customer_service")
}