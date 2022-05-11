package com.example.beersrealmapp.android.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beersrealmapp.BeerData
import com.example.beersrealmapp.BeersDatabase
import com.example.beersrealmapp.android.util.CoroutinesDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeersViewModel @Inject constructor(
    private val beersDatabase: BeersDatabase,
    private val dispatcher: CoroutinesDispatcherProvider,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState = _uiState.asStateFlow()

    fun fetchBeers() {
        viewModelScope.launch(dispatcher.io) {
            beersDatabase.findAllBeers()
                .distinctUntilChanged()
                .collect {
                    if (it.isEmpty()) {
                        _uiState.value = UiState.Empty
                    } else {
                        _uiState.value = UiState.Success(it)
                    }
                }
        }
    }

    fun saveBeer(data: BeerData) {
        viewModelScope.launch(dispatcher.io) {
            beersDatabase.saveBeer(data)
        }
    }

    fun removeBeerById(id: String) {
        viewModelScope.launch(dispatcher.io) {
            beersDatabase.removeBeerById(id)
        }
    }

    fun removeAllBeers() {
        viewModelScope.launch(dispatcher.io) {
            beersDatabase.removeAllBeers()
        }
    }

}