package com.hugg.account.subsidyList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.feature.R
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.feature.component.BlankBtn
import com.hugg.feature.component.BlankBtnWithIcon
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.ACCOUNT_ADD_SUBSIDY
import com.hugg.feature.theme.ACCOUNT_SUBSIDY_MONEY
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.WORD_NEXT
import com.hugg.feature.uiItem.RemoteRound
import com.hugg.feature.uiItem.SubsidyDetailBox
import com.hugg.feature.util.UserInfo

@Composable
fun SubsidyListContainer(
    goToBack : () -> Unit = {},
    onClickCreateEditSubsidyBtn : (Long, CreateOrEditType, Int) -> Unit = { _, _, _-> },
    nowRound : Int = UserInfo.info.round,
    viewModel: SubsidiyListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initRound(nowRound)
    }

    SubsidyListScreen(
        uiState = uiState,
        onClickTopBarLeftBtn = goToBack,
        onClickPrevRoundBtn = { viewModel.onClickPrevRound() },
        onClickNextRoundBtn = { viewModel.onClickNextRound() },
        onClickCreateEditSubsidyBtn = { id, type, _ -> onClickCreateEditSubsidyBtn(id, type, uiState.nowRound) }
    )
}

@Composable
fun SubsidyListScreen(
    uiState : SubsidyListPageState = SubsidyListPageState(),
    onClickTopBarLeftBtn : () -> Unit = {},
    onClickPrevRoundBtn: () -> Unit = {},
    onClickNextRoundBtn: () -> Unit = {},
    onClickCreateRoundBtn: () -> Unit = {},
    onClickCreateEditSubsidyBtn : (Long, CreateOrEditType, Int) -> Unit = { _, _, _ -> },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
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
            middleText = ACCOUNT_SUBSIDY_MONEY
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
        ){
            item {
                Spacer(modifier = Modifier.size(16.dp))

                RemoteRound(
                    onClickPrevRoundBtn = onClickPrevRoundBtn,
                    onClickNextRoundBtn = onClickNextRoundBtn,
                    onClickCreateRoundBtn = onClickCreateRoundBtn,
                    interactionSource = interactionSource,
                    nowRound = uiState.nowRound
                )

                Spacer(modifier = Modifier.size(16.dp))
            }

            item {
                if(uiState.subsidyList.isEmpty()) EmptySubsidyBox(
                    onClickAddSubsidyBtn = onClickCreateEditSubsidyBtn,
                    interactionSource = interactionSource
                )
            }

            itemsIndexed(
                items = uiState.subsidyList,
                key = { _, subsidyVo ->
                    subsidyVo.id
                }
            ) { _, subsidyVo ->
                SubsidyDetailBox(
                    onClickDetailSubsidyBtn = onClickCreateEditSubsidyBtn,
                    interactionSource = interactionSource,
                    item = subsidyVo,
                )

                Spacer(modifier = Modifier.size(8.dp))
            }

            item {
                if(uiState.subsidyList.isNotEmpty()) {
                    Spacer(modifier = Modifier.size(16.dp))

                    BlankBtnWithIcon(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        onClickBtn = { onClickCreateEditSubsidyBtn(-1, CreateOrEditType.CREATE, -1) },
                        text = ACCOUNT_ADD_SUBSIDY
                    )
                }
            }
        }
    }

//    HuggToast(
//        visible = uiState.isShowErrorSpouseCode,
//        text = TOAST_ERROR_NOR_CORRECT_SPOUSE_CODE
//    )
}

@Composable
fun EmptySubsidyBox(
    onClickAddSubsidyBtn : (Long, CreateOrEditType, Int) -> Unit = {_, _, _-> },
    interactionSource: MutableInteractionSource
){
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .aspectRatio(343f / 174f)
            .clickable(
                onClick = { onClickAddSubsidyBtn(-1, CreateOrEditType.CREATE, -1) },
                interactionSource = interactionSource,
                indication = null
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_subsidy_example),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_create_box_top_bar),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = ACCOUNT_ADD_SUBSIDY,
                style = HuggTypography.btn,
                color = Gs70
            )
        }
    }
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    SubsidyListScreen()
}