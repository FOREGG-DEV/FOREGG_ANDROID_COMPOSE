package com.hugg.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.response.challenge.ChallengeCardVo
import com.hugg.feature.R
import com.hugg.feature.component.HuggText
import com.hugg.feature.component.HuggTextField
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.ALL_CHALLENGE_LIST
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.CREATE_MY_CHALLENGE
import com.hugg.feature.theme.EMPTY_SEARCH_RESULT
import com.hugg.feature.theme.Gs60
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.GsWhite
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.MainNormal
import com.hugg.feature.theme.MainStrong
import com.hugg.feature.theme.SEARCH_CHALLENGE
import com.hugg.feature.theme.SEARCH_KEYWORD
import com.hugg.list.detail.ChallengeDetailScreen

@Composable
fun ChallengeListScreen(
    popScreen: () -> Unit,
    goToCreateChallenge: () -> Unit,
    goToChallengeDetail: (Long) -> Unit
) {
    val viewModel: ChallengeListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val interactionSource = remember { MutableInteractionSource() }

    Box {
        ChallengeListContent(
            uiState = uiState,
            popScreen = popScreen,
            goToChallengeDetail = {
                goToChallengeDetail(it.id)
            },
            onValueChange = { viewModel.onInputValueChange(it) },
            onClickBtnSearch = { if(uiState.searchKeyword.isNotEmpty()) viewModel.searchChallenge() },
            interactionSource = interactionSource,
            goToCreateChallenge = goToCreateChallenge
        )
    }
}

@Composable
fun ChallengeListContent(
    uiState: ChallengeListPageState = ChallengeListPageState(),
    popScreen: () -> Unit = {},
    goToChallengeDetail: (ChallengeCardVo) -> Unit = {},
    onValueChange: (String) -> Unit = {},
    onClickBtnSearch: () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    goToCreateChallenge: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(
            leftItemType = TopBarLeftType.BACK,
            middleItemType = TopBarMiddleType.TEXT,
            leftBtnClicked = { popScreen() },
            middleText = ALL_CHALLENGE_LIST
        )
        Spacer(modifier = Modifier.height(16.dp))
        CreateChallengeBox(
            goToCreateChallenge = goToCreateChallenge,
            interactionSource = interactionSource
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchChallengeBar(
            searchKeyword = uiState.searchKeyword,
            onValueChange = onValueChange,
            onClickBtnSearch = onClickBtnSearch,
            interactionSource = interactionSource
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (uiState.emptyKeyword.isNotEmpty() && uiState.challengeList.isEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            HuggText(
                text = String.format(SEARCH_KEYWORD, uiState.emptyKeyword),
                style = HuggTypography.h1,
                color = MainStrong
            )
            Spacer(modifier = Modifier.height(8.dp))
            HuggText(
                text = EMPTY_SEARCH_RESULT,
                style = HuggTypography.h2,
                color = Gs90,
                textAlign = TextAlign.Center
            )
        } else {
            ChallengeListLazyColumn(
                challengeList = uiState.challengeList,
                goToChallengeDetail = goToChallengeDetail,
                interactionSource = interactionSource
            )
        }
    }
}

@Composable
fun CreateChallengeBox(
    goToCreateChallenge: () -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(MainNormal)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { goToCreateChallenge() }
            )
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_create_challenge),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            HuggText(
                text = CREATE_MY_CHALLENGE,
                style = HuggTypography.h3,
                color = GsWhite
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right_white_24),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

@Composable
fun SearchChallengeBar(
    searchKeyword: String = "",
    onValueChange: (String) -> Unit = {},
    onClickBtnSearch: () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val focusManager = LocalFocusManager.current

    HuggTextField(
        value = searchKeyword,
        onValueChange = { onValueChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(GsWhite)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (searchKeyword.isNotEmpty()) {
                    onClickBtnSearch()
                } else {
                    focusManager.clearFocus()
                }
            }
        ),
        textStyle = HuggTypography.p2.copy(color = Gs80),
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (searchKeyword.isEmpty()) {
                        Text(
                            text = SEARCH_CHALLENGE,
                            style = HuggTypography.p2,
                            color = Gs60
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .clickable(
                                indication = null,
                                interactionSource = interactionSource,
                                onClick = { onClickBtnSearch() }
                            )
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
fun ChallengeListLazyColumn(
    challengeList: List<ChallengeCardVo> = emptyList(),
    goToChallengeDetail: (ChallengeCardVo) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(challengeList, key = { it.id }) {
            ChallengeListItem(
                item = it,
                goToChallengeDetail = goToChallengeDetail,
                interactionSource = interactionSource
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ChallengeListItem(
    item: ChallengeCardVo = ChallengeCardVo(),
    goToChallengeDetail: (ChallengeCardVo) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(GsWhite)
            .then(
                if (item.participating) {
                    Modifier.border(
                        width = 1.dp,
                        color = MainNormal,
                        shape = RoundedCornerShape(6.dp)
                    )
                } else Modifier
            )
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = { goToChallengeDetail(item) }
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .padding(start = 12.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = item.image),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                HuggText(
                    text = item.name,
                    style = HuggTypography.h3,
                    color = Gs90
                )
                Spacer(modifier = Modifier.height(3.dp))
                HuggText(
                    text = item.description,
                    style = HuggTypography.p4,
                    color = Gs80,
                    maxLines = 2,
                    overflow = TextOverflow.Clip
                )
            }
        }
    }
}