package com.example.beersrealmapp.android.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.beersrealmapp.BeerData
import com.example.beersrealmapp.android.ui.theme.BeersRealmAppTheme
import java.util.*

@Composable
fun AddBeerScreen(onDoneClick: () -> Unit) {
    val viewModel = hiltViewModel<BeersViewModel>()

    val nameTextState = remember { mutableStateOf(TextFieldValue()) }
    val breweryTextState = remember { mutableStateOf(TextFieldValue()) }
    val abvTextState = remember { mutableStateOf(TextFieldValue()) }

    FormFields(
        nameTextState,
        breweryTextState,
        abvTextState,
        onSave = {
            viewModel.saveBeer(it)
        },
        onDoneClick
    )
}

@Composable
private fun FormFields(
    nameTextState: MutableState<TextFieldValue>,
    breweryTextState: MutableState<TextFieldValue>,
    abvTextState: MutableState<TextFieldValue>,
    onSave: (BeerData) -> Unit,
    onDoneClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        TextField(
            textFieldState = nameTextState,
            label = "Beer Name"
        )
        Spacer(modifier = Modifier.padding(4.dp))
        TextField(
            textFieldState = breweryTextState,
            label = "Brewery Name"
        )
        Spacer(modifier = Modifier.padding(4.dp))
        TextField(
            textFieldState = abvTextState,
            label = "ABV"
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = {
                val name = nameTextState.value.text
                val brewery = breweryTextState.value.text
                val text = abvTextState.value.text
                if (name.isNotEmpty()
                    && brewery.isNotEmpty()
                    && text.isNotEmpty()
                ) {
                    onSave(
                        BeerData(
                            id = UUID.randomUUID().toString(),
                            name = name,
                            brewery = brewery,
                            abv = text.toInt(),
                            imageUrl = "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/beers%2FArcadia_gluten_free.png?alt=media&token=3dffbb0f-8843-4b67-91ee-2e2e4fbae804"
                        )
                    )
                    onDoneClick()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Done")
        }
    }
}

@Composable
fun TextField(
    textFieldState: MutableState<TextFieldValue>,
    label: String
) {
    OutlinedTextField(
        value = textFieldState.value,
        onValueChange = {
            textFieldState.value = it
        },
        placeholder = {
            Text(
                text = label,
                fontSize = 16.sp
            )
        },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(fontSize = 18.sp),
    )
}

@Preview(showBackground = true)
@Composable
fun AddBeerPreview() {
    BeersRealmAppTheme {
        val nameTextState = remember { mutableStateOf(TextFieldValue("Five Lamps Lager")) }
        val breweryTextState = remember { mutableStateOf(TextFieldValue("Five Lamps")) }
        val abvTextState = remember { mutableStateOf(TextFieldValue("5")) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Add Beer")
                    }
                )
            }
        ) {
            FormFields(
                nameTextState = nameTextState,
                breweryTextState = breweryTextState,
                abvTextState = abvTextState,
                onSave = {

                },
                onDoneClick = {

                }
            )
        }
    }
}