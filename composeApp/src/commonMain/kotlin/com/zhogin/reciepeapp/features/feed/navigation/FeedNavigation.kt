package com.zhogin.reciepeapp.features.feed.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.zhogin.reciepeapp.features.app.data.Screen
import com.zhogin.reciepeapp.features.feed.presentation.FeedRoute

fun NavController.navigateToFeed(navOptions: NavOptions? = null) {
    navigate(Screen.Home.route)
}

fun NavGraphBuilder.feedNavGraph(
    isUserLoggedIn: () -> Boolean,
    openLoginBottomSheet: (() -> Unit) -> Unit,
    navigateToDetail: (Long) -> Unit,
    navigateToSearch: () -> Unit,
) {
    composable(Screen.Home.route) {
        FeedRoute(
            navigateToSearch = navigateToSearch,
            navigateToDetail = navigateToDetail,
        )
    }
}