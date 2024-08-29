package com.hugg.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hugg.feature.theme.HuggTheme

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    HuggTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(navController = navController, startDestination = Routes.CalendarGraph.route) {
                signNavGraph(navController)
                calendarGraph(navController)
            }
        }
    }
}