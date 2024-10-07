package com.hugg.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hugg.domain.model.enums.BottomNavType
import com.hugg.feature.R
import com.hugg.feature.theme.Gs50
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.HuggTheme
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.WORD_ACCOUNT
import com.hugg.feature.theme.WORD_CALENDAR
import com.hugg.feature.theme.WORD_DAILY_HUGG
import com.hugg.feature.theme.WORD_HOME
import com.hugg.feature.theme.WORD_MY
import com.hugg.feature.theme.White
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun MainScreen(
    viewModel : MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val interactionSource = remember { MutableInteractionSource() }


    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow
            .distinctUntilChanged()
            .collect { backStackEntry ->
                viewModel.changedRoute(backStackEntry.destination.route)
            }
    }

    HuggTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    NavHost(navController = navController, startDestination = Routes.SignGraph.route) {
                        signNavGraph(navController)
                        calendarGraph(navController)
                        accountGraph(navController)
                        dailyHuggGraph(navController)
                        myPageGraph(navController)
                    }
                }

                // BottomNavView를 하단에 고정
                if (uiState.pageType != BottomNavType.OTHER) {
                    BottomNavView(
                        type = uiState.pageType,
                        interactionSource = interactionSource,
                        navigateToRoute = { route -> navController.navigate(route) }
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavView(
    type : BottomNavType = BottomNavType.OTHER,
    navigateToRoute : (String) -> Unit = {},
    interactionSource: MutableInteractionSource
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White)
            .padding(horizontal = 20.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .clickable(
                    onClick = {
                        if(type == BottomNavType.HOME) return@clickable
                        //navigateToRoute()
                    },
                    interactionSource = interactionSource,
                    indication = null
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                modifier = Modifier.padding(top = 6.dp, start = 10.dp, end = 10.dp ),
                painter = painterResource(id = if (type == BottomNavType.HOME) R.drawable.ic_home_active else R.drawable.ic_home_inactive),
                contentDescription = null
            )

            Spacer(modifier = Modifier.size(3.dp))

            Text(
                modifier = Modifier.padding(bottom = 1.dp),
                text = WORD_HOME,
                style = HuggTypography.p4,
                color = if(type == BottomNavType.HOME) Gs80 else Gs50
            )
        }

        Column(
            modifier = Modifier
                .clickable(
                    onClick = {
                        if(type == BottomNavType.CALENDAR) return@clickable
                        navigateToRoute(Routes.CalendarGraph.route)
                    },
                    interactionSource = interactionSource,
                    indication = null
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Image(
                modifier = Modifier.padding(top = 6.dp, start = 10.dp, end = 10.dp ),
                painter = painterResource(id = if (type == BottomNavType.CALENDAR) R.drawable.ic_calendar_active else R.drawable.ic_calendar_inactive),
                contentDescription = null
            )

            Spacer(modifier = Modifier.size(3.dp))

            Text(
                modifier = Modifier.padding(bottom = 1.dp),
                text = WORD_CALENDAR,
                style = HuggTypography.p4,
                color = if(type == BottomNavType.CALENDAR) Gs80 else Gs50
            )
        }

        Column(
            modifier = Modifier
                .clickable(
                    onClick = {
                        if(type == BottomNavType.DAILY_HUGG) return@clickable
                        navigateToRoute(Routes.DailyHuggGraph.route)
                    },
                    interactionSource = interactionSource,
                    indication = null
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                modifier = Modifier.padding(top = 6.dp, start = 10.dp, end = 10.dp ),
                painter = painterResource(id = if (type == BottomNavType.DAILY_HUGG) R.drawable.ic_daily_hugg_active else R.drawable.ic_daily_hugg_inactive),
                contentDescription = null
            )

            Spacer(modifier = Modifier.size(3.dp))

            Text(
                modifier = Modifier.padding(bottom = 1.dp),
                text = WORD_DAILY_HUGG,
                style = HuggTypography.p4,
                color = if(type == BottomNavType.DAILY_HUGG) Gs80 else Gs50
            )
        }

        Column(
            modifier = Modifier
                .clickable(
                    onClick = {
                        if(type == BottomNavType.ACCOUNT) return@clickable
                        navigateToRoute(Routes.AccountGraph.route)
                    },
                    interactionSource = interactionSource,
                    indication = null
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                modifier = Modifier.padding(top = 6.dp, start = 10.dp, end = 10.dp ),
                painter = painterResource(id = if (type == BottomNavType.ACCOUNT) R.drawable.ic_account_active else R.drawable.ic_account_inactive),
                contentDescription = null
            )

            Spacer(modifier = Modifier.size(3.dp))

            Text(
                modifier = Modifier.padding(bottom = 1.dp),
                text = WORD_ACCOUNT,
                style = HuggTypography.p4,
                color = if(type == BottomNavType.ACCOUNT) Gs80 else Gs50
            )
        }

        Column(
            modifier = Modifier
                .clickable(
                    onClick = {
                        if(type == BottomNavType.PROFILE) return@clickable
                        navigateToRoute(Routes.MyPageGraph.route)
                    },
                    interactionSource = interactionSource,
                    indication = null
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                modifier = Modifier.padding(top = 6.dp, start = 10.dp, end = 10.dp ),
                painter = painterResource(id = if (type == BottomNavType.PROFILE) R.drawable.ic_my_page_active else R.drawable.ic_my_page_inactive),
                contentDescription = null
            )

            Spacer(modifier = Modifier.size(3.dp))

            Text(
                modifier = Modifier.padding(bottom = 1.dp),
                text = WORD_MY,
                style = HuggTypography.p4,
                color = if(type == BottomNavType.PROFILE) Gs80 else Gs50
            )
        }
    }
}