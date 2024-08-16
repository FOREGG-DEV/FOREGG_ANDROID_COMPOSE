package com.hugg.sign.femaleSignUp.surgeryCount

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.feature.component.BlankBtn
import com.hugg.feature.component.SignUpIndicator
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.R


@Composable
fun SurgeryCountContainer(
    navigateSurgeryStart : (Int) -> Unit = {},
    goToBack : () -> Unit = {},
    viewModel: SurgeryCountViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                SurgeryCountEvent.GoToSurgeryStartDayPage -> navigateSurgeryStart(uiState.count)
            }
        }
    }

    SurgeryCountScreen(
        uiState = uiState,
        onClickTopBarLeftBtn = goToBack,
        onClickNextPageBtn = { viewModel.onClickNextBtn() },
        onClickPlusBtn = { viewModel.onClickPlusBtn() },
        onClickMinusBtn = { viewModel.onClickMinusBtn() }
    )
}

@Composable
fun SurgeryCountScreen(
    uiState : SurgeryCountPageState = SurgeryCountPageState(),
    onClickTopBarLeftBtn : () -> Unit = {},
    onClickNextPageBtn : () -> Unit = {},
    onClickPlusBtn : () -> Unit = {},
    onClickMinusBtn : () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {

        TopBar(
            leftItemType = TopBarLeftType.BACK,
            leftBtnClicked = onClickTopBarLeftBtn,
            middleItemType = TopBarMiddleType.TEXT,
            middleText = WORD_SIGN_UP
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(38.dp))

            SignUpIndicator(
                nowPage = 2
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                color = Gs80,
                style = HuggTypography.h1,
                text = SIGN_UP_SURGERY_COUNT
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.Bottom
            ) {

                CountingView(
                    uiState = uiState,
                    onClickMinusBtn = onClickMinusBtn,
                    onClickPlusBtn = onClickPlusBtn
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    style = HuggTypography.h3,
                    color = Gs70,
                    text = WORD_ROUND
                )

            }
        }

        Column(
            verticalArrangement = Arrangement.Bottom
        ) {
            BlankBtn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClickBtn = onClickNextPageBtn,
                text = WORD_NEXT
            )
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun CountingView(
    uiState: SurgeryCountPageState,
    onClickMinusBtn: () -> Unit,
    onClickPlusBtn: () -> Unit,
){
    Row(
        modifier = Modifier
            .width(168.dp)
            .height(48.dp)
            .background(color = White, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.Start
        ){
            Box(
                modifier = Modifier
                    .width(51.dp)
                    .height(48.dp)
                    .background(
                        color = if (uiState.count == 0) Disabled else MainNormal,
                        shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                    )
                    .clickable(
                        onClick = onClickMinusBtn,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = R.drawable.ic_minus_white),
                    contentDescription = null
                )
            }
        }

        Text(
            color = Black,
            style = HuggTypography.h3,
            text = uiState.count.toString()
        )

        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .width(51.dp)
                    .height(48.dp)
                    .background(
                        color = if (uiState.count == 99) Disabled else MainNormal,
                        shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                    )
                    .clickable(
                        onClick = onClickPlusBtn,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_plus_white),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    SurgeryCountScreen()
}