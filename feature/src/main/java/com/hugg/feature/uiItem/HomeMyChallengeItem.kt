package com.hugg.feature.uiItem

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hugg.domain.model.response.challenge.MyChallengeListItemVo
import com.hugg.feature.R
import com.hugg.feature.theme.Gs10
import com.hugg.feature.theme.Gs50
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.WORD_INCOMPLETE
import com.hugg.feature.theme.White
import com.hugg.feature.theme.h3
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.onThrottleClick
import org.threeten.bp.LocalDate

@Composable
fun HomeMyChallengeItem(
    item : MyChallengeListItemVo = MyChallengeListItemVo(),
    context: Context,
    onClickCompleteChallenge : (Long) -> Unit = {},
    interactionSource : MutableInteractionSource
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(80.dp)
            .fillMaxWidth()
            .background(color = if(item.isCompleteToday) Gs10 else White, shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier.size(56.dp),
            model = ImageRequest.Builder(context)
                .data(item.image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.size(4.dp))

        Text(
            text = item.name,
            style = h3(),
            color = Gs90
        )

        Spacer(modifier = Modifier.weight(1f))

        if(item.isCompleteToday){
            Image(
                painter = painterResource(id = R.drawable.ic_home_challenge_complete),
                contentDescription = null
            )
        }
        else{
            Box(
                modifier = Modifier
                    .size(width = 64.dp, height = 40.dp)
                    .background(color = Gs10, shape = RoundedCornerShape(8.dp))
                    .onThrottleClick(
                        onClick = { onClickCompleteChallenge(item.id) },
                        interactionSource = interactionSource
                    ),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = WORD_INCOMPLETE,
                    style = h3(),
                    color = Gs50
                )
            }
        }
    }
}