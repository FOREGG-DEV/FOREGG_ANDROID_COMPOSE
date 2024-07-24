package com.hugg.feature.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugg.feature.R
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.Gs20
import com.hugg.feature.theme.HuggTheme

@Composable
fun Topbar(
    leftContent : @Composable () -> Unit = {},
    rightContent : @Composable () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(57.dp)
            .background(Background)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Start
            ) {
                leftContent()
            }
            Image(
                modifier = Modifier.padding(vertical = 15.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_logo),
                contentDescription = null
            )
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End
            ) {
                rightContent()
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Gs20)
        )
    }
}

@Preview
@Composable
internal fun Preview() {
    HuggTheme {
        Topbar()
    }
}