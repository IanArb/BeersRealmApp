package com.example.beersrealmapp.android

sealed class Screen(val title: String) {
    object HomeScreen : Screen("Beers")
    object AddBeerScreen : Screen("Add Beer")
}