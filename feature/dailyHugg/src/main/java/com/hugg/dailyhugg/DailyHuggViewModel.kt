package com.hugg.dailyhugg

import com.hugg.domain.model.response.dailyHugg.DailyHuggItemVo
import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DailyHuggViewModel @Inject constructor(

): BaseViewModel<DailyHuggPageState>(
    DailyHuggPageState(
        dailyHugg = listOf(DailyHuggItemVo(
            id = 1,
            content = "ㄴㅇ러ㅠㅜㅐㅑㅈㄷ괘댜너히ㅏㅡ피ㅏ읓피ㅑㅜ[ㅐㄱ댜ㅐ[ㅑㅗ3ㅑ헌ㅇㄹ,ㅡㅍ/ㅋ티차ㅡㅜㅠ;ㅍㅇ누ㅐ;ㄹ햐ㅓㅂ제대ㅓㅁㄴ아프/.ㅋㅌ,ㅡㅊㅍ;ㅣㅏㄹ'ㅔ패ㅑ젇게라퓌마ㅜㅐㅑ모곱허ㅜㅁ무ㅐㅑㅗ햐ㅗㄱ허ㅜㅠ리ㅏㅜ키ㅏ푸먀ㅓㄷㄱ햐햐",
            imageUrl = "https://img.khan.co.kr/news/2024/03/23/news-p.v1.20240323.c159a4cab6f64473adf462d873e01e43_P1.jpg",
            reply = "anlkrjgnoaiwe"
        ))
    )
) {

}