package com.hugg.sign.femaleSignUp.startSurgery

import android.app.DatePickerDialog
import android.view.ContextThemeWrapper
import android.widget.DatePicker
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
import androidx.compose.ui.platform.LocalContext
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
import com.hugg.feature.util.ForeggLog
import com.hugg.feature.util.TimeFormatter
import java.util.Calendar


@Composable
fun SurgeryStartContainer(
    navigateFemaleSpouseCode : (String) -> Unit = {},
    goToBack : () -> Unit = {},
    viewModel: SurgeryStartViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            R.style.DatePickerStyle,
            { _, year, month, day ->
                viewModel.selectStartDate(TimeFormatter.getDatePickerDashDate(year, month, day))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                SurgeryStartEvent.GoToSpouseCodePage -> navigateFemaleSpouseCode(uiState.date)
            }
        }
    }

    SurgeryStartScreen(
        uiState = uiState,
        onClickTopBarLeftBtn = goToBack,
        onClickNextPageBtn = { viewModel.onClickNextBtn() },
        onClickDatePickerBtn = { datePickerDialog.show() }
    )
}

@Composable
fun SurgeryStartScreen(
    uiState : SurgeryStartPageState = SurgeryStartPageState(),
    onClickTopBarLeftBtn : () -> Unit = {},
    onClickNextPageBtn : () -> Unit = {},
    onClickDatePickerBtn: () -> Unit = {},
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
                text = SIGN_UP_SURGERY_START
            )

            Spacer(modifier = Modifier.height(24.dp))

            DatePickerView(
                uiState = uiState,
                onClickDatePickerBtn = onClickDatePickerBtn,
                interactionSource = interactionSource
            )
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
fun DatePickerView(
    uiState: SurgeryStartPageState,
    onClickDatePickerBtn: () -> Unit,
    interactionSource : MutableInteractionSource
){
    Row(
        modifier = Modifier
            .height(48.dp)
            .background(color = White, shape = RoundedCornerShape(8.dp))
            .clickable(
                onClick = onClickDatePickerBtn,
                interactionSource = interactionSource,
                indication = null
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Start
        ){
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .background(
                        color = MainNormal,
                        shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                    ),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = R.drawable.ic_calendar_white),
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            modifier = Modifier.padding(end = 27.dp),
            color = Black,
            style = h3(),
            text = uiState.date
        )
    }
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    SurgeryStartScreen()
}