package com.hugg.mypage.myMedicineInjection

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.domain.model.enums.GenderType
import com.hugg.domain.model.enums.HuggTabClickedType
import com.hugg.domain.model.enums.ProfileMedicineInjectionType
import com.hugg.domain.model.enums.RecordType
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.response.profile.MyMedicineInjectionResponseVo
import com.hugg.feature.component.HuggTabBar
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.uiItem.ScheduleDetailItem
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.UserInfo
import com.hugg.feature.util.onThrottleClick


@Composable
fun MyPageMedInjContainer(
    goToBack : () -> Unit = {},
    navigateDetail : (RecordType, Long) -> Unit = {_, _ -> },
    viewModel: MyPageMedInjViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit){
        viewModel.getMyMedicineInjection()
    }

    MyPageMedInjScreen(
        uiState = uiState,
        goToBack = goToBack,
        onClickTab = { type -> viewModel.onClickTab(type) },
        onClickItem = navigateDetail
    )
}

@Composable
fun MyPageMedInjScreen(
    uiState : MyPageMedInjPageState = MyPageMedInjPageState(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    goToBack : () -> Unit = {},
    onClickItem : (RecordType, Long) -> Unit = {_, _ -> },
    onClickTab : (ProfileMedicineInjectionType) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {

        TopBar(
            leftBtnClicked = goToBack,
            leftItemType = TopBarLeftType.BACK,
            middleItemType = TopBarMiddleType.TEXT,
            middleText = if(UserInfo.info.genderType == GenderType.FEMALE) MY_PAGE_MY_MEDICINE_INJECTION else MY_PAGE_SPOUSE_MEDICINE_INJECTION(UserInfo.info.spouse)
        )

        LazyColumn{
            item {
                Spacer(modifier = Modifier.size(16.dp))

                HuggTabBar(
                    leftText = WORD_MEDICINE,
                    onClickLeftTab = { onClickTab(ProfileMedicineInjectionType.MEDICINE) },
                    rightText = WORD_INJECTION,
                    onClickRightTab = { onClickTab(ProfileMedicineInjectionType.INJECTION) },
                    initialTabType = uiState.tabClickedType,
                    tabCount = 2,
                    interactionSource = interactionSource
                )

                Spacer(modifier = Modifier.size(16.dp))
            }

            itemsIndexed(
                items = uiState.itemList,
                key = { _, item ->
                    item.id
                }
            ) { _, item ->
                MyMedInjItem(
                    item = item,
                    interactionSource = interactionSource,
                    onClickItem = onClickItem,
                    type = uiState.tabType
                )

                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

@Composable
fun MyMedInjItem(
    item : MyMedicineInjectionResponseVo = MyMedicineInjectionResponseVo(),
    type : ProfileMedicineInjectionType = ProfileMedicineInjectionType.MEDICINE,
    onClickItem : (RecordType, Long) -> Unit = {_, _ ->},
    interactionSource: MutableInteractionSource
) {
    val recordType = if(type == ProfileMedicineInjectionType.INJECTION) RecordType.INJECTION else RecordType.MEDICINE

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(color = White, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .onThrottleClick(
                onClick = {
                    if(UserInfo.info.genderType == GenderType.MALE) return@onThrottleClick
                    onClickItem(recordType, item.id)
                },
                interactionSource = interactionSource
            )
    ) {
        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = if(item.date.isEmpty()) "${TimeFormatter.getDotsDate(item.startDate)} - ${TimeFormatter.getDotsDate(item.endDate)}" else TimeFormatter.getDotsDate(item.date),
            style = HuggTypography.h4,
            color = Gs70
        )

        Spacer(modifier = Modifier.size(4.dp))

        Text(
            text = item.name,
            style = HuggTypography.h2,
            color = Black
        )

        Spacer(modifier = Modifier.size(8.dp))
    }
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    MyPageMedInjScreen()
}