package com.wemaka.kcourses.feature.login.impl.state

sealed interface SideEffect {
    data object SuccessLogIn : SideEffect
    data object ServerError : SideEffect
    data object UnsuccessLogIn : SideEffect
}
