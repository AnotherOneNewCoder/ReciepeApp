package com.zhogin.reciepeapp.features.app.data

import com.zhogin.reciepeapp.features.detail.navigation.RECIPE_ID_ARG
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import reciepeapp.composeapp.generated.resources.Res
import reciepeapp.composeapp.generated.resources.bookmark_selected
import reciepeapp.composeapp.generated.resources.bookmark_unselected
import reciepeapp.composeapp.generated.resources.detail
import reciepeapp.composeapp.generated.resources.favorites
import reciepeapp.composeapp.generated.resources.home
import reciepeapp.composeapp.generated.resources.home_selected
import reciepeapp.composeapp.generated.resources.home_unselected
import reciepeapp.composeapp.generated.resources.profile
import reciepeapp.composeapp.generated.resources.profile_selected
import reciepeapp.composeapp.generated.resources.profile_unselected
import reciepeapp.composeapp.generated.resources.search
import reciepeapp.composeapp.generated.resources.tabs

sealed class Screen(
    val route: String,
    val resourceId: StringResource,
    val selectedIcon: DrawableResource? = null,
    val unselectedIcon: DrawableResource? = null,
) {
    data object Search: Screen(route = "search", resourceId = Res.string.search)
    data object Tabs: Screen(route = "tabs", resourceId = Res.string.tabs)
    data object Detail: Screen(route = "detail?$RECIPE_ID_ARG={$RECIPE_ID_ARG}", resourceId = Res.string.detail)


    data object Home: Screen(
        route = "home",
        resourceId = Res.string.home,
        selectedIcon = Res.drawable.home_selected,
        unselectedIcon = Res.drawable.home_unselected
    )
    data object Favorites: Screen(
        route = "favorites",
        resourceId = Res.string.favorites,
        selectedIcon = Res.drawable.bookmark_selected,
        unselectedIcon = Res.drawable.bookmark_unselected
    )
    data object Profile: Screen(
        route = "profile",
        resourceId = Res.string.profile,
        selectedIcon = Res.drawable.profile_selected,
        unselectedIcon = Res.drawable.profile_unselected
    )
}