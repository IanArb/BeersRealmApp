package com.example.beersrealmapp.android.ui.theme

sealed class Screen(val title: String) {
    object HomeScreen : Screen("Beers")
    object AddBeerScreen : Screen("Add Beer")
}