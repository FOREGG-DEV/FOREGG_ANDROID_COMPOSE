package com.hugg.sign

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.hugg.feature.theme.HuggTypography

@Composable
fun SignScreen(navHostController: NavHostController) {
    Text(
        text = "Hello 하이욤!",
        style = HuggTypography.btn
    )
}