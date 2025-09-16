package com.zhogin.reciepeapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zhogin.reciepeapp.features.app.data.rememberAppState
import com.zhogin.reciepeapp.features.app.navigation.AppNavHost
import com.zhogin.reciepeapp.features.designSystem.RecipeAppKMPTheme
import com.zhogin.reciepeapp.features.login.presentation.LoginScreenModalBottomSheet
import com.zhogin.reciepeapp.features.login.presentation.LoginViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(
    loginViewModel: LoginViewModel = koinViewModel(),
    navController: NavHostController = rememberNavController()
) {
    RecipeAppKMPTheme {

        KoinContext {

            //val navController = rememberNavController()
            val appState = rememberAppState(
                navController = navController,
                scope = rememberCoroutineScope(),
                appPreferences = koinInject()
            )

            var showLoginBottomSheet by rememberSaveable {
                mutableStateOf(false)
            }

            val isLoggedIn by appState.isLoggedIn.collectAsStateWithLifecycle()
            val isUserLoggedIn: () -> Boolean = {
                isLoggedIn
            }

            var loginCallback: () -> Unit by remember {
                mutableStateOf({})
            }

            val openLoginBottomSheet: (() -> Unit) -> Unit = { callback ->
                showLoginBottomSheet = true
                loginCallback = callback
            }

            val onLoginSuccess: () -> Unit = {
                showLoginBottomSheet = false
                appState.updateIsLoggedIn(true)
                loginViewModel.resetState()
                loginCallback
            }

            val onLogout: () -> Unit = {
                appState.onLogout()
                loginViewModel.resetState()
            }
            val onCloseSheet: () -> Unit = {
                showLoginBottomSheet = false
                loginViewModel.resetState()
            }

            LoginScreenModalBottomSheet(
                showBottomSheet = showLoginBottomSheet,
                onClose = onCloseSheet,
                onLoggingSuccess = onLoginSuccess,
                loginViewModel = loginViewModel
            )

            AppNavHost(
                appState = appState,
                isUserLoggedIn = isUserLoggedIn,
                openLoginBottomSheet = openLoginBottomSheet,
                onLogout = onLogout,
            )
        }


    }
}