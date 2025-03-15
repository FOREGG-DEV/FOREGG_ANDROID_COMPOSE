package com.hugg.sign.splash

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.feature.R
import com.hugg.feature.component.HuggDialog
import com.hugg.feature.component.HuggExplainDialog
import com.hugg.feature.theme.ACCOUNT_LIST_DIALOG_DELETE
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.Sunday
import com.hugg.feature.theme.UPDATE_DIALOG_BUTTON
import com.hugg.feature.theme.UPDATE_DIALOG_CONTENT
import com.hugg.feature.theme.UPDATE_DIALOG_TITLE
import com.hugg.feature.theme.WORD_DELETE

@Composable
fun HuggSplashContainer(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToOnboarding : () -> Unit = {},
    navigateToHome : () -> Unit = {},
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getVersion()
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                SplashEvent.GoToMainEvent -> navigateToHome()
                SplashEvent.GoToSignEvent -> navigateToOnboarding()
            }
        }
    }

    if(uiState.showUpdateDialog){
        HuggExplainDialog(
            title = UPDATE_DIALOG_TITLE,
            explain = UPDATE_DIALOG_CONTENT,
            positiveText = UPDATE_DIALOG_BUTTON,
            onClickCancel = { viewModel.updateShowUpdateDialog(false) },
            onClickPositive = { goToPlayStore(context) },
        )
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

private fun goToPlayStore(context : Context){
    val packageName = context.packageName
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
        webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(webIntent)
    }
}