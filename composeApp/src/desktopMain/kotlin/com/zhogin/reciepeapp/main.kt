package com.zhogin.reciepeapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.zhogin.reciepeapp.di.initKoinJvm

val koin = initKoinJvm()

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "RecipeApp",
    ) {
        App()
    }
}