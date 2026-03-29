package com.wemaka.kcourses.navigation

import androidx.navigation.NavController
import androidx.navigation.navOptions


/**
 * Очищает стек до нужного пункта назначения
 * @param route пункт назначения, к которому осуществляется переход
 * @param popUpTo baseRoute класса BaseDestination - пункт назначения, до которого необходимо очистить стэк
 * @param inclusive должен ли пункт назначения popUpTo быть извлечен из заднего стека
 */
fun NavController.navigateNewTask(
    route: BaseRoute,
    popUpTo: BaseRoute = route,
    inclusive: Boolean = true
) {
    navigate(
        route = route,
        navOptions = navOptions {
            popUpTo(popUpTo) { this.inclusive = inclusive }
            launchSingleTop = true
        }
    )
}

/**
 * Переход к новому пункту с сохранением текущего состояния для последующего восстановления. При
 * переходе пытается восстановить состояние целевого пункта навигации.
 * @param route идентификатор пункта, к которому нужно переходить
 * @param root граф, до которого необходимо очистить стэк. Должен быть главным для текущего
 * пункта и целевого
 */
fun NavController.navigateCached(route: BaseRoute, root: BaseRoute) {
    navigate(route) {
        popUpTo(root) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}