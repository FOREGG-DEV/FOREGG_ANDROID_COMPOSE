package com.hugg.sign.accessPermission

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.feature.component.BlankBtn
import com.hugg.feature.component.SignUpIndicator
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.R


@Composable
fun AccessPermissionContainer(
    navigateInputSsn : () -> Unit = {},
    goToBack : () -> Unit = {},
) {
    val context = LocalContext.current

    // 권한 요청 런처 설정
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { _ -> }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // 카메라 권한이 승인되었을 때 처리할 내용
            println("카메라 권한 승인")
        } else {
            // 카메라 권한이 거부되었을 때 처리할 내용
            println("카메라 권한 거부")
        }
    }

    val photoPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // 저장소(사진) 권한이 승인되었을 때 처리할 내용
            println("사진 권한 승인")
        } else {
            // 저장소(사진) 권한이 거부되었을 때 처리할 내용
            println("사진 권한 거부")
        }
    }

    AccessPermissionScreen(
        onClickTopBarLeftBtn = goToBack,
        onClickNextPageBtn = navigateInputSsn
    )
}

@Composable
fun AccessPermissionScreen(
    onClickTopBarLeftBtn : () -> Unit = {},
    onClickNextPageBtn : () -> Unit = {},
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
                nowPage = 1
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                color = Gs80,
                style = HuggTypography.h1,
                text = SIGN_UP_ACCESS_PERMISSION_TITLE
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                modifier = Modifier.padding(start = 4.dp),
                color = Gs70,
                style = HuggTypography.h3,
                text = SIGN_UP_ACCESS_PERMISSION_CONTENT
            )

            Spacer(modifier = Modifier.height(33.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = White, shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = SIGN_UP_ACCESS_PERMISSION_DETAIL_EXPLAIN,
                    style = HuggTypography.p3_l,
                    color = Gs80,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = SIGN_UP_ACCESS_PERMISSION_OPTIONAL_TITLE,
                    style = HuggTypography.p2,
                    color = MainStrong,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp)
                            .padding(12.dp),
                        painter = painterResource(id = R.drawable.ic_notification_empty_gs_80),
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    Column {
                        Text(
                            text = WORD_NOTIFICATION,
                            style = HuggTypography.h3,
                            color = Gs90
                        )

                        Spacer(modifier = Modifier.size(2.dp))

                        Text(
                            text = SIGN_UP_ACCESS_PERMISSION_NOTIFICATION,
                            style = HuggTypography.p2_l,
                            color = Gs90
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp)
                            .padding(12.dp),
                        painter = painterResource(id = R.drawable.ic_camera_empty_gs_80),
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    Column {
                        Text(
                            text = SIGN_UP_ACCESS_PERMISSION_PHOTO_CAMERA,
                            style = HuggTypography.h3,
                            color = Gs90
                        )

                        Spacer(modifier = Modifier.size(2.dp))

                        Text(
                            text = DAILY_HUGG,
                            style = HuggTypography.p2_l,
                            color = Gs90
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = SIGN_UP_ACCESS_PERMISSION_CHANGE_PERMISSION,
                    style = HuggTypography.p2,
                    color = MainStrong,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = SIGN_UP_ACCESS_PERMISSION_CHANGE_PERMISSION_EXPLAIN,
                    style = HuggTypography.h4,
                    color = Gs90,
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.Bottom
        ) {
            BlankBtn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClickBtn = onClickNextPageBtn,
                text = WORD_NEXT,
            )

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    AccessPermissionScreen()
}