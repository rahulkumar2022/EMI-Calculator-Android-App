package com.synergeticsciences.emicalculator.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Result : Screen("result")
    object Amortization : Screen("amortization")
    object History : Screen("history")
    object Settings : Screen("settings")
}

