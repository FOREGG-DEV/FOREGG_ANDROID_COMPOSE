package com.hugg.feature.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hugg.domain.model.enums.DialogType
import com.hugg.feature.theme.Black
import com.hugg.feature.theme.Gs10
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.MainNormal
import com.hugg.feature.theme.Sunday
import com.hugg.feature.theme.WORD_NO
import com.hugg.feature.theme.WORD_YES
import com.hugg.feature.theme.White

@Composable
fun HuggDialog(
    title : String = "",
    dialogType : DialogType = DialogType.DOUBLE,
    negativeColor : Color = Gs10,
    positiveColor : Color = MainNormal,
    negativeText : String = WORD_NO,
    positiveText : String = WORD_YES,
    onClickNegative : () -> Unit = {},
    onClickPositive : () -> Unit = {},
    onClickCancel : () -> Unit = {},
    hasWarningText : Boolean = false,
    warningMessage : String = "",
    interactionSource : MutableInteractionSource
){
    Dialog(
        onDismissRequest = onClickCancel,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ){
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(164.dp)
                .background(color = White, shape = RoundedCornerShape(12.dp))
        ) {
            Spacer(modifier = Modifier.size(if(hasWarningText) 24.dp else 40.dp))

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                maxLines = 1,
                text = title,
                style = HuggTypography.h2,
                color = Black,
                textAlign = TextAlign.Center
            )
            
            if(hasWarningText) {
                Spacer(modifier = Modifier.size(2.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    maxLines = 1,
                    text = warningMessage,
                    style = HuggTypography.p1_l,
                    color = Sunday,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                if(dialogType == DialogType.DOUBLE) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .background(color = negativeColor, shape = RoundedCornerShape(4.dp))
                            .clickable(
                                onClick = onClickNegative,
                                interactionSource = interactionSource,
                                indication = null
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = negativeText,
                            style = HuggTypography.h2,
                            color = Gs80
                        )
                    }

                    Spacer(modifier = Modifier.size(8.dp))
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .background(color = positiveColor, shape = RoundedCornerShape(4.dp))
                        .clickable(
                            onClick = {
                                onClickCancel()
                                onClickPositive()
                            },
                            interactionSource = interactionSource,
                            indication = null
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = positiveText,
                        style = HuggTypography.h2,
                        color = White
                    )
                }
            }

            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}