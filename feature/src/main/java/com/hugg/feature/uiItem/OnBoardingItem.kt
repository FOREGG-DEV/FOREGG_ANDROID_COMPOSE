package com.hugg.feature.uiItem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hugg.feature.R
import com.hugg.feature.component.HuggText
import com.hugg.feature.theme.Black
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.h1
import com.hugg.feature.theme.p1

@Composable
fun OnBoardingItem(
    image : Int = R.drawable.onboarding_first,
    title : String = "",
    content : String = ""
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = ImageVector.vectorResource(image),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(12.dp))
        HuggText(
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
            text = title,
            style = HuggTypography.h1,
            color = Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        HuggText(
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
            text = content,
            style = HuggTypography.p1,
            color = Gs80
        )
    }
}
