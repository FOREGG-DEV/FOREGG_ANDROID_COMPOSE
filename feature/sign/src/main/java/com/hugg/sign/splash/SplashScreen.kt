package com.hugg.sign.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hugg.feature.R
import com.hugg.feature.theme.Background

@Composable
fun HuggSplashContainer(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToOnboarding : () -> Unit = {},
    navigateToHome : () -> Unit = {},
){
    LaunchedEffect(Unit) {
        viewModel.checkLogin()
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                SplashEvent.GoToMainEvent -> navigateToHome()
                SplashEvent.GoToSignEvent -> navigateToOnboarding()
            }
        }
    }

    HuggSplashScreen()
}

@Composable
fun HuggSplashScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.size(252.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.size(112.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_splash_line),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.size(75.dp))
    }
}