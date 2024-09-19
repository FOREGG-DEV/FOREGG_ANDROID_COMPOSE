package com.hugg.main

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
}