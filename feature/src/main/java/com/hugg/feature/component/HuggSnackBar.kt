package com.hugg.feature.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.White

@Composable
fun HuggSnackBar(
    text : String = "",
    padding : Dp = 0.dp
) {
    Box(
       modifier = Modifier
           .fillMaxWidth()
           .height(40.dp)
           .padding(padding)
           .background(color = Color(0xCC2C2C2C), shape = RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ){
        Text(
            color = White,
            style = HuggTypography.p2,
            text = text,
            textAlign = TextAlign.Center,
        )
    }
}