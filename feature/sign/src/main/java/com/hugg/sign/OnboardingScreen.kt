package com.hugg.sign

import Logo
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hugg.feature.component.Topbar
import com.hugg.feature.icon.ArrowBackGs60
import com.hugg.feature.theme.Gs60
import com.hugg.feature.theme.HuggTheme
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.ONBOARDING_SKIP

@Composable
fun OnboardingScreen(
    navHostController: NavHostController
) {
    Topbar(
        leftContent = {
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .padding(12.dp),
                imageVector = ArrowBackGs60,
                contentDescription = null
            )
        },
        rightContent = {
            Text(
                modifier = Modifier
                    .padding(horizontal = 11.dp, vertical = 14.dp),
                text = ONBOARDING_SKIP,
                color = Gs60,
                style = HuggTypography.h4
            )
            Spacer(modifier = Modifier.width(17.dp))
        }
    )
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    Topbar(
        leftContent = {
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .padding(12.dp),
                imageVector = ArrowBackGs60,
                contentDescription = null
            )
        },
        rightContent = {
            Text(
                modifier = Modifier
                    .padding(horizontal = 11.dp, vertical = 14.dp),
                text = ONBOARDING_SKIP,
                color = Gs60,
                style = HuggTypography.h4
            )
            Spacer(modifier = Modifier.width(17.dp))
        }
    )
}