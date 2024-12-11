package com.hugg.home.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.domain.model.enums.NotificationType
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.uiItem.NotificationListItem


@Composable
fun NotificationContainer(
    navigateGoToChallengeCheer : (Long) -> Unit = {},
    navigateGoToDailyHuggDetail: (String) -> Unit = {},
    goToBack : () -> Unit = {},
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val interactionSource = remember { MutableInteractionSource() }
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val lastVisibleItem = visibleItems.lastOrNull()
                if (lastVisibleItem != null && lastVisibleItem.index == uiState.notificationList.size - 1) {
                    viewModel.getNextPageNotificationList()
                }
            }
    }


    NotificationScreen(
        uiState = uiState,
        interactionSource = interactionSource,
        navigateGoToChallengeCheer = navigateGoToChallengeCheer,
        navigateGoToDailyHuggDetail = navigateGoToDailyHuggDetail,
        goToBack = goToBack
    )
}

@Composable
fun NotificationScreen(
    uiState : NotificationPageState = NotificationPageState(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    navigateGoToChallengeCheer : (Long) -> Unit = {},
    navigateGoToDailyHuggDetail : (String) -> Unit = {},
    goToBack : () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {

        TopBar(
            leftItemType = TopBarLeftType.BACK,
            leftBtnClicked = goToBack,
            middleText = WORD_NOTIFICATION,
            middleItemType = TopBarMiddleType.TEXT
        )

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ){
            item {
                Spacer(modifier = Modifier.size(24.dp))
            }

            itemsIndexed(
                items = uiState.notificationList,
                key = { _, notificationVo ->
                    notificationVo.id
                }
            ) { _, notificationVo ->
                NotificationListItem(
                    item = notificationVo,
                    interactionSource = interactionSource,
                    onCLickNotification = { type ->
                        when(type){
                            NotificationType.CLAP,
                            NotificationType.SUPPORT -> { navigateGoToChallengeCheer(notificationVo.targetKey.toLong()) }
                            NotificationType.REPLY -> { navigateGoToDailyHuggDetail(notificationVo.targetKey) }
                        }
                    }
                )
            }
        }

    }
}