package com.zhogin.reciepeapp.features.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.zhogin.reciepeapp.features.app.data.AppState
import com.zhogin.reciepeapp.features.app.data.Screen
import com.zhogin.reciepeapp.features.detail.navigation.detailNavGraph
import com.zhogin.reciepeapp.features.search.navigation.searchNavGraph
import com.zhogin.reciepeapp.features.tabs.navigation.tabsNavGraph

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    appState: AppState,
    startDestination: String = Screen.Tabs.route,
    isUserLoggedIn: () -> Boolean,
    openLoginBottomSheet: (() -> Unit) -> Unit,
    onLogout: () -> Unit,
) {
    val navController = appState.navController
    val tabNavController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        tabsNavGraph(
            tabNavController = tabNavController,
            navigateToDetail = {
                appState.navigateToDetail(it)
            },
            isUserLoggedIn = isUserLoggedIn,
            openLoginBottomSheet = openLoginBottomSheet,
            onLogout = onLogout,
            navigateToSearch = appState::navigateToSearch,
        )
        searchNavGraph(
            navigateToDetail = {
                appState.navigateToDetail(it)
            },
            onBackPressed = appState::navigateBack
        )
        detailNavGraph(
            onBackClick = appState::navigateBack,
            isUserLoggedIn = isUserLoggedIn,
            openLoginBottomSheet = openLoginBottomSheet,
        )
    }
}