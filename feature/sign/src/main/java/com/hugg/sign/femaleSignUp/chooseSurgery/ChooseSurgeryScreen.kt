package com.hugg.sign.femaleSignUp.chooseSurgery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.domain.model.enums.SurgeryType
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.feature.component.BlankBtn
import com.hugg.feature.component.SignUpIndicator
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.R


@Composable
fun ChooseSurgeryContainer(
    navigateSpouseCodePage : (String) -> Unit = {},
    navigateSurgeryCountPage : (String) -> Unit = {},
    goToBack : () -> Unit = {},
    viewModel: ChooseSurgeryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                ChooseSurgeryEvent.GoToSurgeryCountPage -> navigateSurgeryCountPage(uiState.surgeryType.type)
                ChooseSurgeryEvent.GoToSpouseCodePage -> navigateSpouseCodePage(uiState.surgeryType.type)
            }
        }
    }

    ChooseSurgeryScreen(
        uiState = uiState,
        onClickTopBarLeftBtn = goToBack,
        onClickNextPageBtn = { viewModel.onClickNextBtn() },
        onClickDropDown = { viewModel.onClickDropDown() },
        onClickMenuType = { type -> viewModel.onClickType(type)},
        interactionSource = interactionSource
    )
}

@Composable
fun ChooseSurgeryScreen(
    uiState : ChooseSurgeryPageState = ChooseSurgeryPageState(),
    onClickTopBarLeftBtn : () -> Unit = {},
    onClickNextPageBtn : () -> Unit = {},
    onClickDropDown : () -> Unit = {},
    onClickMenuType : (SurgeryType) -> Unit = {},
    interactionSource : MutableInteractionSource = remember { MutableInteractionSource() }
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
            middleText = WORD_SIGN_UP,
            interactionSource = interactionSource
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
                style = h1(),
                text = SIGN_UP_CHOOSE_SURGERY
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(White, shape = RoundedCornerShape(8.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    color = Gs90,
                    style = h3(),
                    text = uiState.surgeryType.type
                )

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    horizontalArrangement = Arrangement.End
                ){
                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .width(48.dp)
                        .background(
                            MainNormal,
                            shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                        ),
                        contentAlignment = Alignment.Center
                    ){
                        Image(
                            modifier = Modifier
                                .padding(top = 4.dp, bottom = 1.dp, start = 2.dp, end = 2.dp)
                                .clickable(
                                    onClick = onClickDropDown,
                                    interactionSource = interactionSource,
                                    indication = null
                                ),
                            painter = painterResource(R.drawable.ic_drop_down_white),
                            contentDescription = null
                        )
                    }
                }
            }

            if(uiState.isExpandMenu) SurgeryDropDown(onClickMenuType, interactionSource)
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
fun SurgeryDropDown(
    onClickType : (SurgeryType) -> Unit = {},
    interactionSource : MutableInteractionSource
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, shape = RoundedCornerShape(8.dp), clip = false)
            .background(White, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
    ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(White)
            .clickable(
                onClick = { onClickType(SurgeryType.THINK_SURGERY) },
                interactionSource = interactionSource,
                indication = null
            ),
            contentAlignment = Alignment.CenterStart
        ){
            Text(
                modifier = Modifier.padding(start = 12.dp),
                style = h3(),
                color = Gs70,
                text = SurgeryType.THINK_SURGERY.type
            )
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(White)
            .clickable(
                onClick = { onClickType(SurgeryType.IUI) },
                interactionSource = interactionSource,
                indication = null
            ),
            contentAlignment = Alignment.CenterStart
        ){
            Text(
                modifier = Modifier.padding(start = 12.dp),
                style = h3(),
                color = Gs70,
                text = SurgeryType.IUI.type
            )
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(White)
            .clickable(
                onClick = { onClickType(SurgeryType.EGG_FREEZING) },
                interactionSource = interactionSource,
                indication = null
            ),
            contentAlignment = Alignment.CenterStart
        ){
            Text(
                modifier = Modifier.padding(start = 12.dp),
                style = h3(),
                color = Gs70,
                text = SurgeryType.EGG_FREEZING.type
            )
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(White)
            .clickable(
                onClick = { onClickType(SurgeryType.IN_VITRO_FERTILIZATION) },
                interactionSource = interactionSource,
                indication = null
            ),
            contentAlignment = Alignment.CenterStart
        ){
            Text(
                modifier = Modifier.padding(start = 12.dp),
                style = h3(),
                color = Gs70,
                text = SurgeryType.IN_VITRO_FERTILIZATION.type
            )
        }
    }
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    ChooseSurgeryScreen()
}