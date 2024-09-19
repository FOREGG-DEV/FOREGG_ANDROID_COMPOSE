package com.example.dailyhugg.create

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.hugg.dailyhugg.R
import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.CREATE_TITLE
import com.hugg.feature.theme.DAILY_HUGG
import com.hugg.feature.theme.DAILY_HUGG_CONTENT_HINT
import com.hugg.feature.theme.Gs10
import com.hugg.feature.theme.GsWhite
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.IMAGE_PERMISSION_TEXT
import com.hugg.feature.theme.WORD_REGISTRATION
import com.hugg.feature.util.HuggToast
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.UserInfo

@Composable
fun CreateDailyHuggScreen(

) {
    val viewModel: CreateDailyHuggViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val interactionSource = remember { MutableInteractionSource() }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
        }
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            galleryLauncher.launch("image/*")
        } else {
            HuggToast.createToast(context = context, message = IMAGE_PERMISSION_TEXT).show()
        }
    }
    val year = TimeFormatter.getYear(TimeFormatter.getToday())
    val formattedMDW = TimeFormatter.getDateFormattedMDWKor(TimeFormatter.getToday())

    CreateDailyHuggContent(
        selectedImageUri = selectedImageUri,
        permissionLauncher = permissionLauncher,
        uiState = uiState,
        onDailyHuggContentChanged = { viewModel.onDailyHuggContentChange(it) },
        year = year,
        formattedMDW = formattedMDW,
        onSelectedDailyConditionType = { viewModel.onSelectedDailyCondition(it) },
        interactionSource = interactionSource
    )
}

@Composable
fun CreateDailyHuggContent(
    selectedImageUri: Uri? = null,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>? = null,
    uiState: CreateDailyHuggPageState = CreateDailyHuggPageState(),
    onDailyHuggContentChanged: (String) -> Unit = {},
    year: Int = 2024,
    formattedMDW: String = "",
    interactionSource: MutableInteractionSource = remember {  MutableInteractionSource() },
    onSelectedDailyConditionType: (DailyConditionType) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBar(
                middleText = DAILY_HUGG,
                middleItemType = TopBarMiddleType.TEXT,
                leftItemType = TopBarLeftType.CLOSE
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = String.format(
                        CREATE_TITLE,
                        UserInfo.info.name,
                        year,
                        formattedMDW
                    ),
                    style = HuggTypography.h1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                BtnDailyCondition(
                    onSelectedDailyConditionType = onSelectedDailyConditionType,
                    interactionSource = interactionSource
                )

                Spacer(modifier = Modifier.height(24.dp))

                DailyHuggInputField(
                    content = uiState.dailyHuggContent,
                    onDailyHuggContentChanged = onDailyHuggContentChanged
                )

                Spacer(modifier = Modifier.height(8.dp))

                BtnImageSelector(
                    selectedImageUri = selectedImageUri,
                    permissionLauncher = permissionLauncher
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 80.dp)
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(uiState.btnRegistrationColor)
                .align(Alignment.BottomCenter)
                .clickable(
                    enabled = uiState.clickable,
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {}
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = WORD_REGISTRATION,
                style = HuggTypography.btn,
                color = GsWhite
            )
        }
    }
}

@Composable
fun BtnDailyCondition(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onSelectedDailyConditionType: (DailyConditionType) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BtnDailyConditionItem(
            dailyConditionType = DailyConditionType.WORST,
            interactionSource = interactionSource,
            onSelectedDailyConditionType = onSelectedDailyConditionType
        )
        BtnDailyConditionItem(
            dailyConditionType = DailyConditionType.BAD,
            interactionSource = interactionSource,
            onSelectedDailyConditionType = onSelectedDailyConditionType
        )
        BtnDailyConditionItem(
            dailyConditionType = DailyConditionType.SOSO,
            interactionSource = interactionSource,
            onSelectedDailyConditionType = onSelectedDailyConditionType
        )
        BtnDailyConditionItem(
            dailyConditionType = DailyConditionType.GOOD,
            interactionSource = interactionSource,
            onSelectedDailyConditionType = onSelectedDailyConditionType
        )
        BtnDailyConditionItem(
            dailyConditionType = DailyConditionType.PERFECT,
            interactionSource = interactionSource,
            onSelectedDailyConditionType = onSelectedDailyConditionType
        )
    }
}

@Composable
fun BtnDailyConditionItem(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    dailyConditionType: DailyConditionType = DailyConditionType.DEFAULT,
    onSelectedDailyConditionType: (DailyConditionType) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .size(62.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onSelectedDailyConditionType(dailyConditionType) }
            )
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dailyConditionType.value,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun DailyHuggInputField(
    content: String = "",
    onDailyHuggContentChanged: (String) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(343f / 173f)
            .clip(RoundedCornerShape(8.dp))
            .background(GsWhite)
    ) {
        BasicTextField(
            value = content,
            onValueChange = { onDailyHuggContentChanged(it) },
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .align(Alignment.TopStart),
            textStyle = HuggTypography.p2,
            decorationBox = { innerTextField ->
                if (content.isEmpty()) {
                    Text(
                        text = DAILY_HUGG_CONTENT_HINT,
                        color = Color.Gray,
                        style = HuggTypography.p2,
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                }
                innerTextField()
            }
        )
    }
}

@Composable
fun BtnImageSelector(
    selectedImageUri: Uri? = null,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>? = null,
) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Gs10)
            .clickable {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher?.launch(Manifest.permission.READ_MEDIA_IMAGES)
                } else {
                    permissionLauncher?.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        if (selectedImageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(model = selectedImageUri),
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = com.hugg.feature.R.drawable.ic_camera),
                contentDescription = ""
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCreateDaily() {
    CreateDailyHuggContent()
}