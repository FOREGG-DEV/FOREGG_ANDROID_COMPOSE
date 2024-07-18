package com.hugg.data.api

import com.hugg.data.base.ApiResponse
import com.hugg.data.dto.schedule.ScheduleListResponse
import com.hugg.data.dto.schedule.ScheduleSideEffectResponse
import com.hugg.domain.model.request.calendar.AddMedicalRecordRequest
import com.hugg.domain.model.request.calendar.ScheduleDetailRequestVo
import com.hugg.domain.model.vo.calendar.ScheduleDetailVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ScheduleApi {

    companion object{
        const val PATH_ID = "id"
        const val QUERY_YEAR_AND_MONTH = "yearmonth"
    }
    @PUT(Endpoints.RECORD.MODIFY)
    suspend fun modify(
        @Path(PATH_ID) id : Long,
        @Body request : ScheduleDetailRequestVo
    ) : Response<ApiResponse<Unit>>

    @POST(Endpoints.RECORD.MEDICAL_RECORD)
    suspend fun addMedicalRecord(
        @Path(PATH_ID) id : Long,
        @Body request : AddMedicalRecordRequest
    ) : Response<ApiResponse<Unit>>

    @POST(Endpoints.RECORD.ADD)
    suspend fun addSchedule(
        @Body request : ScheduleDetailRequestVo
    ) : Response<ApiResponse<Unit>>

    @GET(Endpoints.RECORD.MEDICAL_RECORD_AND_SIDE_EFFECT)
    suspend fun getMedicalRecordAndSideEffect(
        @Path(PATH_ID) id : Long
    ) : Response<ApiResponse<ScheduleSideEffectResponse>>

    @DELETE(Endpoints.RECORD.DELETE)
    suspend fun deleteSchedule(
        @Path(PATH_ID) id : Long
    ) : Response<ApiResponse<Unit>>

    @GET(Endpoints.RECORD.DETAIL)
    suspend fun getDetailSchedule(
        @Path(PATH_ID) id : Long
    ) : Response<ApiResponse<ScheduleDetailVo>>

    @GET(Endpoints.RECORD.SCHEDULE_LIST)
    suspend fun getScheduleList(
        @Query(QUERY_YEAR_AND_MONTH) yearmonth : String
    ) : Response<ApiResponse<ScheduleListResponse>>
}