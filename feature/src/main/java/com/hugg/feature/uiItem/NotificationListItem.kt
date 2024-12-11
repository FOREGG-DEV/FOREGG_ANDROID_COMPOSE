package com.hugg.feature.uiItem

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hugg.domain.model.enums.NotificationType
import com.hugg.domain.model.response.notification.NotificationHistoryItemResponseVo
import com.hugg.feature.component.HuggText
import com.hugg.feature.theme.Gs50
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.NOTIFICATION_CHEER
import com.hugg.feature.theme.NOTIFICATION_CLAP
import com.hugg.feature.theme.NOTIFICATION_REPLY
import com.hugg.feature.theme.White
import com.hugg.feature.util.onThrottleClick

@Composable
fun NotificationListItem(
    item : NotificationHistoryItemResponseVo,
    interactionSource: MutableInteractionSource,
    onCLickNotification : (NotificationType) -> Unit,
) {
    val message = when(item.notificationType){
        NotificationType.CLAP -> NOTIFICATION_CLAP
        NotificationType.SUPPORT -> NOTIFICATION_CHEER
        NotificationType.REPLY -> NOTIFICATION_REPLY
    }

    Row(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 12.dp, vertical = 16.dp)
            .onThrottleClick(
                onClick = { onCLickNotification(item.notificationType) },
                interactionSource = interactionSource
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HuggText(
            modifier = Modifier
                .padding(end = 5.dp)
                .weight(1f),
            text = item.sender + message,
            style = HuggTypography.h3,
            color = Gs90
        )

        HuggText(
            text = item.elapsedTime,
            style = HuggTypography.p3_l,
            color = Gs50
        )
    }
}