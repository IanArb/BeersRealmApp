package com.example.beersrealmapp.android.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.beersrealmapp.BeerData
import com.example.beersrealmapp.android.ui.theme.BeersRealmAppTheme

@Composable
fun BeersScreen() {
    val viewModel = hiltViewModel<BeersViewModel>()

    viewModel.fetchBeers()

    val state = viewModel.uiState.collectAsState()

    when (val uiState = state.value) {
        is UiState.Success -> {
            Column {
                Text(
                    modifier = Modifier
                        .padding(end = 16.dp, top = 16.dp)
                        .align(Alignment.End)
                        .clickable {
                            viewModel.removeAllBeers()
                        },
                    text = "Clear All",
                    fontWeight = FontWeight.Medium,
                    color = Color.Blue
                )

                BeersList(
                    beers = uiState.beers,
                    onDismissBeer = {
                        viewModel.removeBeerById(it)
                    }
                )
            }
        }
        is UiState.Empty -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("No beers here.. add some. I'm thirsty!")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BeersList(
    beers: List<BeerData>,
    onDismissBeer: (String) -> Unit
) {
    LazyColumn {
        items(beers, { it.id }) { item ->
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                        onDismissBeer(item.id)
                    }
                    true
                }
            )

            SwipeToDismiss(
                state = dismissState,
                directions = setOf(
                    DismissDirection.StartToEnd,
                    DismissDirection.EndToStart
                ),
                dismissThresholds = {
                    FractionalThreshold(0.2f)
                },
                background = {
                    val direction = dismissState.dismissDirection ?: return@SwipeToDismiss

                    val color by animateColorAsState(
                        when (dismissState.targetValue) {
                            DismissValue.Default -> Color.LightGray
                            DismissValue.DismissedToEnd -> Color.Green
                            DismissValue.DismissedToStart -> Color.Red
                        }
                    )

                    val icon = when (direction) {
                        DismissDirection.StartToEnd -> Icons.Default.Done
                        DismissDirection.EndToStart -> Icons.Default.Delete
                    }

                    val scale by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f)

                    val alignment = when (direction) {
                        DismissDirection.EndToStart -> Alignment.CenterEnd
                        DismissDirection.StartToEnd -> Alignment.CenterStart
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(start = 12.dp, end = 12.dp),
                        contentAlignment = alignment
                    ) {
                        Icon(
                            icon,
                            contentDescription = "Icon",
                            modifier = Modifier.scale(scale)
                        )
                    }
                },
                dismissContent = {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 12.dp,
                                bottom = 12.dp,
                                start = 16.dp,
                                end = 16.dp
                            ),
                        elevation = animateDpAsState(targetValue = if (dismissState.dismissDirection != null) 4.dp else 0.dp).value
                    ) {
                        CardContent(item)
                    }
                },
            )
        }
    }
}

@Composable
private fun CardContent(item: BeerData) {
    Row {
        Image(
            painter = rememberImagePainter(
                data = item.imageUrl,
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .size(120.dp, 150.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.padding(6.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.name,
                color = Color.Black,
                fontWeight = FontWeight.Black,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
            Spacer(Modifier.padding(4.dp))
            Text(
                text = item.brewery,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp
            )
            Spacer(Modifier.padding(4.dp))
            Text(
                text = "ABV - ${item.abv}",
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    BeersRealmAppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Beers")
                    }
                )
            }
        ) {
            BeersList(
                listOf(
                    BeerData(
                        id = "",
                        name = "Five Lamps Lager",
                        brewery = "Five Lamps",
                        imageUrl = "",
                        abv = 5
                    ),
                    BeerData(
                        id = "",
                        name = "Five Lamps Lager",
                        brewery = "Five Lamps",
                        imageUrl = "",
                        abv = 5
                    ),
                    BeerData(
                        id = "",
                        name = "Five Lamps Lager",
                        brewery = "Five Lamps",
                        imageUrl = "",
                        abv = 5
                    )
                ),
                onDismissBeer = {

                }
            )
        }
    }
}