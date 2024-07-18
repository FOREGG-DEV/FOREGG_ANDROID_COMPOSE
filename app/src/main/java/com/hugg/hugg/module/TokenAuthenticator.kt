package com.hugg.hugg.module

import android.util.Log
import com.hugg.domain.base.ApiState
import com.hugg.domain.model.request.sign.SaveForeggJwtRequestVo
import com.hugg.domain.model.response.sign.ForeggJwtResponseVo
import com.hugg.domain.repository.ForeggJwtRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val foreggJwtRepository: ForeggJwtRepository,
) : Authenticator {
    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: okhttp3.Response): Request? = runBlocking {
        val access = async { foreggJwtRepository.getAccessToken().first() }
        val refresh = async { foreggJwtRepository.getRefreshToken().first() }
        val accessToken = access.await()
        val refreshToken = refresh.await()

        mutex.withLock {
            if (verifyTokenIsRefreshed(accessToken, refreshToken)) {
                Log.d("RETROFIT","TokenAuthenticator - authenticate() called / 중단된 API 재요청")
                response.request
                    .newBuilder()
                    .removeHeader("Authorization")
                    .header(
                        "Authorization",
                        "Bearer " + foreggJwtRepository.getAccessToken().first()
                    )
                    .build()
            } else null
        }
    }

    private suspend fun verifyTokenIsRefreshed(
        access: String,
        refresh: String
    ): Boolean {
        val newAccess = foreggJwtRepository.getAccessToken().first()

        return if (access != newAccess) true else {
            Log.d("RETROFIT","TokenAuthenticator - authenticate() called / 토큰 만료. 토큰 Refresh 요청: $refresh")
            var foreggJwtToken = ForeggJwtResponseVo("", "")
            foreggJwtRepository.reIssueToken(refresh).collect { state ->
                when(state) {
                    is ApiState.Loading -> { }
                    is ApiState.Success -> {
                        foreggJwtToken = state.data
                        return@collect
                    }
                    else -> {
                        return@collect
                    }
                }
            }

            val savePlubJwtRequestVo = SaveForeggJwtRequestVo(foreggJwtToken.accessToken, foreggJwtToken.refreshToken)

            foreggJwtRepository.saveAccessTokenAndRefreshToken(savePlubJwtRequestVo).first()
            foreggJwtToken.isTokenValid.apply {
                if(!this) Log.d("RETROFIT","TokenAuthenticator - verifyTokenIsRefreshed() called / 토큰 갱신 실패.")
            }
        }
    }
}