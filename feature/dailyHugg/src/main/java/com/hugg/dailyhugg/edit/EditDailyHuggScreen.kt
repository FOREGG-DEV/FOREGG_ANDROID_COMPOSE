package com.hugg.dailyhugg.edit

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.dailyhugg.create.CreateEditDailyHuggContent
import com.hugg.dailyhugg.create.CreateEditDailyHuggEvent
import com.hugg.dailyhugg.create.CreateEditDailyHuggPageState
import com.hugg.dailyhugg.create.CreateEditDailyHuggViewModel
import com.hugg.dailyhugg.create.getFilePartFromUri
import com.hugg.domain.model.enums.DailyHuggType
import com.hugg.feature.theme.COMPLETE_EDIT_DAILY_HUGG
import com.hugg.feature.theme.IMAGE_PERMISSION_TEXT
import com.hugg.feature.util.HuggToast
import com.hugg.feature.util.TimeFormatter
import kotlinx.coroutines.launch

@Composable
fun EditDailyHuggScreen(
    id: Long?,
    popScreen: () -> Unit = {},
    goToImgPreview: (Uri?) -> Unit = {},
    getSavedUri: () -> Uri?,
    dailyHuggPageState: CreateEditDailyHuggPageState?
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
    val isInitialized = rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (isInitialized.value) {
            viewModel.setSelectedImageUri(getSavedUri())
        } else {
            viewModel.setPageState(dailyHuggPageState, id)
            isInitialized.value = true
        }
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                CreateEditDailyHuggEvent.CompleteEditDailyHugg -> {
                    HuggToast.createToast(context = context, COMPLETE_EDIT_DAILY_HUGG).show()
                    popScreen()
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
        onClickBtnEdit = {
            coroutineScope.launch {
                val imagePart = uiState.selectedImageUri?.let { uri ->
                    getFilePartFromUri(context = context, uri = uri)
                }
                viewModel.editDailyHugg(imagePart)
            }
        },
        popScreen = popScreen,
        dailyHuggType = DailyHuggType.EDIT
    )
}