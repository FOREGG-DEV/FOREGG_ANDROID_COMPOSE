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
const val WORD_NO = "아니요"
const val WORD_YES = "네"
const val WORD_CONFIRM = "확인"
const val WORD_DELETE = "삭제"
const val WORD_CALENDAR = "캘린더"
const val WORD_ACCOUNT = "가계부"
const val WORD_HOSPITAL = "병원"
const val WORD_INJECTION = "주사"
const val WORD_MEDICINE = "약"
const val WORD_ETC = "기타"
const val WORD_REGISTRATION = "등록"
const val WORD_MODIFY = "수정"

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

// --------- 캘린더 --------- //
const val CALENDAR_EMPTY_SCHEDULE = "이날은 일정이 없어요"
const val CALENDAR_MAX_SCHEDULE = "일정을 더 추가할 수 없어요 (하루 최대 7개)"
const val CALENDAR_MEDICINE_UNIT = "정"
const val CALENDAR_INJECTION_UNIT = "IU"

// --------- 가계부 --------- //
const val ACCOUNT_ALL = "전체"
const val ACCOUNT_ROUND = "회차별"
const val ACCOUNT_ROUND_UNIT = "회차"
const val ACCOUNT_MONTH = "월별"
const val ACCOUNT_PERSONAL = "개인"
const val ACCOUNT_SUBSIDY_ALL = "지원금 전체"
const val ACCOUNT_ALL_EXPENSE = "총 지출"
const val ACCOUNT_SUBSIDY = "지원"
const val ACCOUNT_CHOOSE_DATE = "기간 선택"
const val ACCOUNT_DIVIDE_DATE = "-"
const val ACCOUNT_ADD_ROUND = "회차 추가"
const val ACCOUNT_SUGGEST_ADD_SUBSIDY = "지원금을 추가해보세요"
const val ACCOUNT_AVAILABLE_MONEY = "사용 가능 금액"
const val ACCOUNT_MONEY_UNIT = "원"

// --------- 지원급 --------- //
const val ACCOUNT_SUBSIDY_MONEY = "지원금"
const val ACCOUNT_ADD_SUBSIDY = "지원금 추가"
const val ACCOUNT_MODIFY_SUBSIDY = "지원금 수정"
const val ACCOUNT_EXPENDITURE = "지출"
const val ACCOUNT_SUBSIDY_NICKNAME = "지원금 별명*"
const val ACCOUNT_MAX_TWO_WORD = "최대 2글자"
const val ACCOUNT_NICKNAME_TEXT_FIELD_HINT = "지원금 별명 입력"
const val ACCOUNT_NICKNAME_DETAIL_EXPLAIN = "지원(별명)으로 표시될 두글자를 적어주세요."
const val ACCOUNT_SUBSIDY_CONTENT = "지원금 내용"
const val ACCOUNT_CONTENT_TEXT_FIELD_HINT = "지원금 내용 입력"
const val ACCOUNT_SUBSIDY_MONEY_TITLE = "지원금 총액*"

const val ACCOUNT_DIALOG_SUBSIDY_DELETE = "지원금을 삭제할까요?"

const val ACCOUNT_TOAST_SUCCESS_CREATE_SUBSIDY = "지원금이 등록되었어요"
const val ACCOUNT_TOAST_SUCCESS_EDIT_SUBSIDY = "지원금이 수정되었어요"
const val ACCOUNT_TOAST_SUCCESS_DELETE_SUBSIDY = "지원금이 삭제되었어요"

// --------- 에러 Toast --------- //
const val TOAST_ERROR_NOR_CORRECT_SPOUSE_CODE = "유효하지 않은 배우자코드입니다."