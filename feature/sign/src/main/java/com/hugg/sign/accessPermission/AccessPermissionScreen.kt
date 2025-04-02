package com.hugg.sign.accessPermission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.feature.component.BlankBtn
import com.hugg.feature.component.SignUpIndicator
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.R
import com.hugg.feature.component.HuggText
import com.hugg.feature.util.ForeggLog


@Composable
fun AccessPermissionContainer(
    navigateInputSsn : () -> Unit = {},
    goToBack : () -> Unit = {},
) {
    val context = LocalContext.current

    val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.CAMERA
        )
    } else {
        arrayOf(
            Manifest.permission.CAMERA
        )
    }

    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
    }

    LaunchedEffect(Unit) {
        checkAndRequestPermissions(
            context,
            permissions,
            launcherMultiplePermissions
        )
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

            HuggText(
                color = Gs80,
                style = HuggTypography.h1,
                text = SIGN_UP_ACCESS_PERMISSION_TITLE
            )

            Spacer(modifier = Modifier.height(3.dp))

            HuggText(
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
                HuggText(
                    modifier = Modifier.fillMaxWidth(),
                    text = SIGN_UP_ACCESS_PERMISSION_DETAIL_EXPLAIN,
                    style = HuggTypography.p3_l,
                    color = Gs80,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                HuggText(
                    text = SIGN_UP_ACCESS_PERMISSION_OPTIONAL_TITLE,
                    style = HuggTypography.p2,
                    color = MainStrong,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_notification_empty_gs_80),
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    Column {
                        HuggText(
                            text = WORD_NOTIFICATION,
                            style = HuggTypography.h3,
                            color = Gs90
                        )

                        Spacer(modifier = Modifier.size(2.dp))

                        HuggText(
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
                        painter = painterResource(id = R.drawable.ic_camera_empty_gs_80),
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    Column {
                        HuggText(
                            text = SIGN_UP_ACCESS_PERMISSION_PHOTO_CAMERA,
                            style = HuggTypography.h3,
                            color = Gs90
                        )

                        Spacer(modifier = Modifier.size(2.dp))

                        HuggText(
                            text = DAILY_HUGG,
                            style = HuggTypography.p2_l,
                            color = Gs90
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                HuggText(
                    text = SIGN_UP_ACCESS_PERMISSION_CHANGE_PERMISSION,
                    style = HuggTypography.p2,
                    color = MainStrong,
                )

                Spacer(modifier = Modifier.height(4.dp))

                HuggText(
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

private fun checkAndRequestPermissions(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
) {
    if (permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }){ return }
    else {
        launcher.launch(permissions)
    }
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    AccessPermissionScreen()
}