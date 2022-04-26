package com.example.beersrealmapp.android.presentation

import com.example.beersrealmapp.BeerData

sealed class UiState {
    data class Success(val beers: List<BeerData>) : UiState()
    object Empty : UiState()
}