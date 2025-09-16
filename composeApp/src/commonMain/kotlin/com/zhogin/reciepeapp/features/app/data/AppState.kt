package com.zhogin.reciepeapp.features.app.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import com.zhogin.reciepeapp.features.app.data.AppConstants.IS_LOGGED_IN
import com.zhogin.reciepeapp.features.detail.navigation.navigateToDetail
import com.zhogin.reciepeapp.features.search.navigation.navigateToSearch
import com.zhogin.reciepeapp.features.tabs.navigation.navigateToTabs
import com.zhogin.reciepeapp.preferences.AppPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun rememberAppState(
    navController: NavHostController,
    scope: CoroutineScope = rememberCoroutineScope(),
    appPreferences: AppPreferences,
): AppState {
    return remember(
        navController,
        scope
    ) {
        AppState(navController, scope, appPreferences)
    }
}

@Stable
class AppState(
    val navController: NavHostController,
    scope: CoroutineScope,
    private val appPreferences: AppPreferences,
) {
    private val _isLoggedIn = MutableStateFlow(appPreferences.getBoolean(IS_LOGGED_IN, false))
    val isLoggedIn = _isLoggedIn.asStateFlow()


    fun navigateToTabs() = navController.navigateToTabs()

    fun navigateToDetail(id: Long) = navController.navigateToDetail(id)
    fun navigateBack() = navController.navigateUp()
    fun navigateToSearch() = navController.navigateToSearch()

    fun updateIsLoggedIn(isLoggedIn: Boolean) {
        this._isLoggedIn.value = isLoggedIn
        appPreferences.putBoolean(IS_LOGGED_IN, isLoggedIn)
    }
    fun onLogout() {
        updateIsLoggedIn(false)
    }
}