package com.zhogin.reciepeapp.features.tabs.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zhogin.reciepeapp.features.app.data.Screen
import com.zhogin.reciepeapp.features.favorites.navigation.favoritesNavGraph
import com.zhogin.reciepeapp.features.feed.navigation.feedNavGraph
import com.zhogin.reciepeapp.features.profile.navigation.profileNavGraph
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

val tabItems = listOf(
    Screen.Home,
    Screen.Favorites,
    Screen.Profile,
)

@Composable
fun TabsRoute(
    tabNavController: NavHostController,
    navigateToDetail: (Long) -> Unit,
    isUserLoggedIn: () -> Boolean,
    openLoginBottomSheet: (() -> Unit) -> Unit,
    onLogout: () -> Unit,
    navigateToSearch: () -> Unit,
){
    TabsScreen(
        tabNavController = tabNavController,
        navigateToDetail = navigateToDetail,
        isUserLoggedIn = isUserLoggedIn,
        openLoginBottomSheet = openLoginBottomSheet,
        onLogout = onLogout,
        navigateToSearch = navigateToSearch
    )
}

@Composable
fun TabsScreen(
    tabNavController: NavHostController,
    navigateToDetail: (Long) -> Unit,
    navigateToSearch: () -> Unit,
    isUserLoggedIn: () -> Boolean,
    openLoginBottomSheet: (() -> Unit) -> Unit,
    onLogout: () -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.onPrimary
            ) {

                val navBackStackEntry by tabNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                tabItems.forEach { topLevelRoute ->
                    val isSelected = currentDestination?.hierarchy?.any { it.route == topLevelRoute.route} == true
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        ),
                        selected = isSelected,
                        icon = {
                            val icon = if (isSelected) topLevelRoute.selectedIcon else topLevelRoute.unselectedIcon
                            val color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                            icon?.let {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    painter = painterResource(icon),
                                    contentDescription = topLevelRoute.route,
                                    tint = color,
                                )
                            }
                        },
                        onClick = {
                            tabNavController.navigate(topLevelRoute.route) {
                                popUpTo(tabNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = {
                            Text(stringResource(topLevelRoute.resourceId))
                        },
//                        colors =
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            tabNavController, startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            feedNavGraph(
                navigateToDetail = navigateToDetail,
                isUserLoggedIn = isUserLoggedIn,
                openLoginBottomSheet = openLoginBottomSheet,
                navigateToSearch = navigateToSearch
            )

            favoritesNavGraph(
                navigateToDetail = navigateToDetail,
                isUserLoggedIn = isUserLoggedIn,

            )
            profileNavGraph(
                isUserLoggedIn = isUserLoggedIn,
                openLoginBottomSheet = openLoginBottomSheet,
                onLogout = onLogout,
            )

        }
    }
}