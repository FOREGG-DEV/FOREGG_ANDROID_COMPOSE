package com.hugg.feature.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface ThrottleClickInterface {
    fun event(event: () -> Unit)
}

@Composable
fun <T> throttleClickEvent(
    content: @Composable (ThrottleClickInterface) -> T,
): T {
    val interval = 500L
    var canClick by remember { mutableStateOf(true) }

    val result = content(
        object : ThrottleClickInterface {
            override fun event(event: () -> Unit) {
                if (canClick) {
                    canClick = false
                    event()

                    CoroutineScope(Dispatchers.Main).launch {
                        delay(interval)
                        canClick = true
                    }
                }
            }
        }
    )

    return result
}

@Composable
fun Modifier.onThrottleClick(
    onClick: () -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) = composed {
    throttleClickEvent { manager ->
        this.then(
            clickable(
                onClick = { manager.event { onClick() } },
                indication = null,
                interactionSource = interactionSource
            )
        )
    }
}