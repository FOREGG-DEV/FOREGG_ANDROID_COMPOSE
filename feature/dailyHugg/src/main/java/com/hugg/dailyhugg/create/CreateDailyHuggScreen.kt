package com.hugg.dailyhugg.create

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.domain.model.enums.DailyHuggType
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.feature.component.FilledBtn
import com.hugg.feature.component.HuggText
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.ALREADY_EXIST_DAILY_HUGG
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.DAILY_HUGG
import com.hugg.feature.theme.DAILY_HUGG_CONTENT_HINT
import com.hugg.feature.theme.Gs10
import com.hugg.feature.theme.GsWhite
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.IMAGE_PERMISSION_TEXT
import com.hugg.feature.theme.MainStrong
import com.hugg.feature.theme.WORD_MODIFY
import com.hugg.feature.theme.WORD_REGISTRATION
import com.hugg.feature.util.HuggToast
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URL

@Composable
fun CreateDailyHuggScreen(
    goToDailyHuggCreationSuccessScreen: () -> Unit = {},
    popScreen: () -> Unit = {},
    goToImgPreview: (Uri?) -> Unit = {},
    getSavedUri: () -> Uri?,
) {
    val viewModel: CreateEditDailyHuggViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.setSelectedImageUri(it)
            goToImgPreview(it)
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
    val onClickBtnClose = { viewModel.setSelectedImageUri(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.setSelectedImageUri(getSavedUri())
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                CreateEditDailyHuggEvent.AlreadyExistEditDailyHugg -> {
                    HuggToast.createToast(context = context, message = ALREADY_EXIST_DAILY_HUGG).show()
                }
                CreateEditDailyHuggEvent.GoToDailyHuggCreationSuccess -> {
                    goToDailyHuggCreationSuccessScreen()
                }
            }
        }
    }

    CreateEditDailyHuggContent(
        permissionLauncher = permissionLauncher,
        uiState = uiState,
        onDailyHuggContentChanged = { viewModel.onDailyHuggContentChange(it) },
        year = year,
        formattedMDW = formattedMDW,
        onSelectedDailyConditionType = { viewModel.onSelectedDailyCondition(it) },
        interactionSource = interactionSource,
        onClickBtnClose = onClickBtnClose,
        onClickBtnCreate = {
            coroutineScope.launch {
                val imagePart = uiState.selectedImageUri?.let { uri ->
                    getFilePartFromUri(context = context, uri = uri)
                }
                viewModel.createDailyHugg(imagePart)
            }
        },
        popScreen = popScreen,
        dailyHuggType = DailyHuggType.CREATE
    )
}

@Composable
fun CreateEditDailyHuggContent(
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>? = null,
    uiState: CreateEditDailyHuggPageState = CreateEditDailyHuggPageState(),
    onDailyHuggContentChanged: (String) -> Unit = {},
    year: Int = 2024,
    formattedMDW: String = "",
    interactionSource: MutableInteractionSource = remember {  MutableInteractionSource() },
    onSelectedDailyConditionType: (DailyConditionType) -> Unit = {},
    onClickBtnClose: () -> Unit = {},
    onClickBtnCreate: () -> Unit = {},
    onClickBtnEdit: () -> Unit = {},
    popScreen: () -> Unit = {},
    dailyHuggType: DailyHuggType = DailyHuggType.CREATE
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
                leftItemType = TopBarLeftType.CLOSE,
                leftBtnClicked = { popScreen() }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                HuggText(
                    text = buildAnnotatedString {
                        append(String.format("%s님\n", UserInfo.info.name))
                        withStyle(style = SpanStyle(color = MainStrong)) {
                            append(String.format("%s년 %s", year, formattedMDW))
                        }
                        append("\n오늘 하루 어떠셨나요?")
                    },
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

                if (uiState.selectedImageUri == null) {
                    BtnImageSelector(
                        permissionLauncher = permissionLauncher,
                        interactionSource = interactionSource
                    )
                } else {
                    SelectedImageItem(
                        selectedImageUri = uiState.selectedImageUri,
                        onClickBtnClose = onClickBtnClose,
                        interactionSource = interactionSource
                    )
                }
            }
        }

        FilledBtn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 80.dp)
                .align(Alignment.BottomCenter),
            isActive = uiState.clickable,
            onClickBtn = {
                when(dailyHuggType) {
                    DailyHuggType.CREATE -> onClickBtnCreate()
                    DailyHuggType.EDIT -> onClickBtnEdit()
                }
            },
            text = when(dailyHuggType) {
                DailyHuggType.CREATE -> WORD_REGISTRATION
                DailyHuggType.EDIT -> WORD_MODIFY
            }
        )
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
                CompositionLocalProvider(LocalDensity provides Density(density = LocalDensity.current.density, fontScale = 1f)){
                    if (content.isEmpty()) {
                        HuggText(
                            text = DAILY_HUGG_CONTENT_HINT,
                            color = Color.Gray,
                            style = HuggTypography.p2,
                            modifier = Modifier.align(Alignment.TopStart)
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun BtnImageSelector(
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Gs10)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher?.launch(Manifest.permission.READ_MEDIA_IMAGES)
                    } else {
                        permissionLauncher?.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = com.hugg.feature.R.drawable.ic_camera),
            contentDescription = ""
        )
    }
}

@Composable
fun SelectedImageItem(
    selectedImageUri: Uri,
    onClickBtnClose: () -> Unit,
    interactionSource: MutableInteractionSource
) {
    Box(
        modifier = Modifier
            .size(width = 128.dp, height = 80.dp)
            .clip(RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = selectedImageUri),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .size(16.dp)
                .padding(2.dp)
                .align(Alignment.TopEnd)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { onClickBtnClose() }
                )
        ) {
            Image(
                painter = painterResource(id = com.hugg.feature.R.drawable.ic_circle_close),
                contentDescription = ""
            )
        }
    }
}

suspend fun getFilePartFromUri(context: Context, uri: Uri): MultipartBody.Part? {
    return if (uri.scheme == "https" || uri.scheme == "http") {
        val file = downloadImageFromUrl(context, uri.toString()) ?: return null
        val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        MultipartBody.Part.createFormData("image", file.name, requestBody)
    } else {
        val contentResolver = context.contentResolver
        val fileDescriptor = contentResolver.openFileDescriptor(uri, "r") ?: return null
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val file = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(file)

        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()

        val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        MultipartBody.Part.createFormData("image", file.name, requestBody)
    }
}

suspend fun downloadImageFromUrl(context: Context, urlString: String): File? {
    return try {
        withContext(Dispatchers.IO) {
            val url = URL(urlString)
            val connection = url.openConnection()
            connection.connect()

            val inputStream = connection.getInputStream()
            val file = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")
            val outputStream = FileOutputStream(file)

            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            file
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCreateDaily() {
    CreateEditDailyHuggContent()
}