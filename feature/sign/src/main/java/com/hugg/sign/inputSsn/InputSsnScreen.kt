package com.hugg.sign.inputSsn

import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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


@Composable
fun InputSsnContainer(
    accessToken : String = "",
    viewModel: InputSsnViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusRequesters = remember { List(7) { FocusRequester() } }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
        keyboardController?.show()
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect {
            handleEvent(it as InputSsnEvent, focusRequesters)
        }
    }

    InputSsnScreen(
        uiState = uiState,
        focusRequesters = focusRequesters,
        onClickKey = { event, position -> viewModel.onClickKey(event, position) },
        onChangedValue = { value, position -> viewModel.onChangedText(value, position) },
    )
}

fun handleEvent(
    event: InputSsnEvent,
    focusRequesters : List<FocusRequester>,
){
    when(event) {
        InputSsnEvent.GoToFemaleSignUp -> {}
        InputSsnEvent.GoToMaleSignUp -> {}
        is InputSsnEvent.FocusTextFiled -> focusRequesters[event.position].requestFocus()
    }
}

@Composable
fun InputSsnScreen(
    uiState : InputSsnPageState = InputSsnPageState(),
    focusRequesters : List<FocusRequester>,
    onClickTopBarLeftBtn : () -> Unit = {},
    onClickNextPageBtn : () -> Unit = {},
    onClickKey : (KeyEvent, Int) -> Unit = {_, _ -> },
    onChangedValue : (String, Int) -> Unit = {_, _ -> },
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
                nowPage = 1
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                style = HuggTypography.h1,
                text = SIGN_UP_INPUT_SSN
            )

            Spacer(modifier = Modifier.height(12.dp))

            SsnInputView(
                uiState = uiState,
                focusRequesters = focusRequesters,
                onClickKey = onClickKey,
                onChangedValue = onChangedValue
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
fun SsnInputView(
    uiState : InputSsnPageState,
    focusRequesters : List<FocusRequester>,
    onClickKey : (KeyEvent, Int) -> Unit = {_, _ -> },
    onChangedValue : (String, Int) -> Unit = {_, _ -> },
) {

    Row {
        SsnItemView(value = uiState.ssn1, focusRequester = focusRequesters[0], position = 0, onClickKey = onClickKey, onChangedValue = onChangedValue)
        Spacer(modifier = Modifier.width(2.dp))

        SsnItemView(value = uiState.ssn2, focusRequester = focusRequesters[1], position = 1, onClickKey = onClickKey, onChangedValue = onChangedValue)
        Spacer(modifier = Modifier.width(2.dp))

        SsnItemView(value = uiState.ssn3, focusRequester = focusRequesters[2], position = 2, onClickKey = onClickKey, onChangedValue = onChangedValue)
        Spacer(modifier = Modifier.width(2.dp))

        SsnItemView(value = uiState.ssn4, focusRequester = focusRequesters[3], position = 3, onClickKey = onClickKey, onChangedValue = onChangedValue)
        Spacer(modifier = Modifier.width(2.dp))

        SsnItemView(value = uiState.ssn5, focusRequester = focusRequesters[4], position = 4, onClickKey = onClickKey, onChangedValue = onChangedValue)
        Spacer(modifier = Modifier.width(2.dp))

        SsnItemView(value = uiState.ssn6, focusRequester = focusRequesters[5], position = 5, onClickKey = onClickKey, onChangedValue = onChangedValue)
        Spacer(modifier = Modifier.width(6.dp))

        SsnItemView(value = uiState.ssn7, focusRequester = focusRequesters[6], position = 6, onClickKey = onClickKey, onChangedValue = onChangedValue)
        Spacer(modifier = Modifier.width(2.dp))
    }
}

@Composable
fun SsnItemView(
    value : String,
    focusRequester : FocusRequester,
    position : Int,
    onClickKey : (KeyEvent, Int) -> Unit,
    onChangedValue : (String, Int) -> Unit
){
    Box(
        modifier = Modifier
            .width(29.dp)
            .height(48.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(White),
        contentAlignment = Alignment.Center
    ){
        BasicTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .onPreviewKeyEvent { event ->
                    onClickKey(event, position)
                    false
                },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            value = value,
            onValueChange = { value ->
                onChangedValue(value, position)
            },
            textStyle = HuggTypography.h3.copy(
                textAlign = TextAlign.Center
            ),
            singleLine = true,
        )
    }
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    InputSsnScreen(
        focusRequesters = List(7) { FocusRequester() }
    )
}