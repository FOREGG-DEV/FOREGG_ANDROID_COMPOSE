package com.hugg.challenge.main

import com.hugg.feature.base.Event

sealed class ChallengeMainEvent: Event {
    data object DuplicateNickname: ChallengeMainEvent()
    data object ExistNickname: ChallengeMainEvent()
    data object OnSuccessOpenChallenge: ChallengeMainEvent()
    data object ChallengeAlreadyParticipated: ChallengeMainEvent()
    data object InsufficientPoint: ChallengeMainEvent()
}