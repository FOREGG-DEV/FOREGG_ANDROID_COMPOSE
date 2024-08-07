package com.hugg.feature.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugg.feature.theme.Gs10
import com.hugg.feature.theme.Sub

@Composable
fun SignUpIndicator(
    nowPage : Int = 1,
    allPage : Int = 3,
){
    val nowPosition = nowPage - 1
    Row {
        repeat(allPage) {
            if(it < nowPosition) Box(
                modifier = Modifier
                    .width(10.dp)
                    .height(10.dp)
                    .background(color = Sub, shape = RoundedCornerShape(size = 999.dp))
            )
            if(it == nowPosition) Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(10.dp)
                    .background(color = Sub, shape = RoundedCornerShape(size = 999.dp))
            )
            if(it > nowPosition) Box(
                modifier = Modifier
                    .width(10.dp)
                    .height(10.dp)
                    .background(color = Gs10, shape = RoundedCornerShape(size = 999.dp))
            )
            Spacer(modifier = Modifier.width(2.dp))
        }
    }
}

@Preview
@Composable
internal fun SignUpIndicatorPreView() {
    SignUpIndicator()
}