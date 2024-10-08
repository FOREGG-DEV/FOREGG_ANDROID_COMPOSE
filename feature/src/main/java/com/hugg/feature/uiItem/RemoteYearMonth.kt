package com.hugg.feature.uiItem

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.hugg.feature.R
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.h2

@Composable
fun RemoteYearMonth(
    onClickPrevMonthBtn : () -> Unit = {},
    onClickNextMonthBtn : () -> Unit = {},
    date : String = "",
    interactionSource : MutableInteractionSource
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .padding(12.dp)
                .clickable(
                    onClick = onClickPrevMonthBtn,
                    interactionSource = interactionSource,
                    indication = null
                ),
            imageVector = ImageVector.vectorResource(R.drawable.ic_back_arrow_gs_60),
            contentDescription = null
        )

        Spacer(modifier = Modifier.size(9.dp))

        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = date,
            color = Gs90,
            style = h2()
        )

        Spacer(modifier = Modifier.size(9.dp))

        Image(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .padding(12.dp)
                .graphicsLayer(scaleX = -1f)
                .clickable(
                    onClick = onClickNextMonthBtn,
                    interactionSource = interactionSource,
                    indication = null
                ),
            imageVector = ImageVector.vectorResource(R.drawable.ic_back_arrow_gs_60),
            contentDescription = null
        )

        Spacer(modifier = Modifier.size(9.dp))

    }
}