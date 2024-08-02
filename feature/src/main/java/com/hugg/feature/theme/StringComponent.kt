package com.hugg.feature.theme

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

// --------- 단어 --------- //
const val WORD_SKIP = "skip"
const val WORD_NEXT = "다음"
const val WORD_DAILY_RECORD = "하루기록"
const val WORD_SIGN_UP = "회원가입"
const val WORD_ROUND = "회차"

// --------- 기타 --------- //
const val COPY_COMPLETE_TEXT = "클립보드에 배우자 코드가 복사되었어요!"

// --------- 온보딩 --------- //
const val ONBOARDING_TITLE_1 = "주사 맞을 시간엔 알람이 울려요"
const val ONBOARDING_TITLE_2 = "여보, 우리 내일부터 과배란 주사지?"
const val ONBOARDING_TITLE_3 = "총 얼마 썼는지 궁금할 땐?"
const val ONBOARDING_TITLE_4 = "생활습관 챌린지로 건강하게"

const val ONBOARDING_CONTENT_1 = "정확한 시간에 맞아야 하는 주사,\n허그가 챙길 테니 안심하세요."
const val ONBOARDING_CONTENT_2 = "진료 내용, 복약 스케줄, 하루 동안 느낀 감정까지.\n모든 과정을 남편과 공유해요."
const val ONBOARDING_CONTENT_3 = "가계부 기능을 통해, 정부지원금과 개인 지출을\n구분하여 확인할 수 있어요."
const val ONBOARDING_CONTENT_4 = "난자는 영양 상태, 호르몬 변화에 큰 영향을 받아요.\n챌린지를 통해 건강한 생활습관을 지켜나가요."

// --------- 회원가입 --------- //
val SIGN_UP_INPUT_SSN = buildAnnotatedString {
    withStyle(style = SpanStyle(color = Gs80)) {
        append("주민번호 ")
    }
    withStyle(style = SpanStyle(color = MainNormal)) {
        append("앞 7자리")
    }
    withStyle(style = SpanStyle(color = Gs80)) {
        append("를\n적어주세요")
    }
}
const val SIGN_UP_DIVIDE_SSN = "-"
const val SIGN_UP_CHOOSE_SURGERY = "현재 받고 있는 시술을\n선택해주세요."
const val SIGN_UP_SURGERY_COUNT = "현재 진행중인 회차를\n알려주세요."
const val SIGN_UP_SURGERY_START = "치료 시작 날짜를\n알려주세요."
const val SIGN_UP_SPOUSE_CODE_FEMALE = "배우자 코드를 남편에게\n공유해주세요."
const val SIGN_UP_SPOUSE_CODE_FEMALE_HINT = "남편 회원가입시 붙여넣기 해주세요.\n아내 가입 완료 시 남편 로그인이 가능합니다."
const val SIGN_UP_SPOUSE_CODE_MALE_HINT = "아내에게 받은 공유 코드를 입력해주세요."
const val SIGN_UP_COMPLETE = "가입 완료"
const val SIGN_UP_MALE = "아내에게 받은 배우자 코드를\n입력해주세요."