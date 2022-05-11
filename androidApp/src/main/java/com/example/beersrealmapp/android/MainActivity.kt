package com.example.beersrealmapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.beersrealmapp.android.presentation.AddBeerScreen
import com.example.beersrealmapp.android.presentation.BeersScreen
import com.example.beersrealmapp.android.ui.theme.BeersRealmAppTheme
import com.example.beersrealmapp.android.ui.theme.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            BeersRealmAppTheme {
                Scaffold(
                    topBar = {
                        if (shouldShowForScreen(navController = navController, route = Screen.HomeScreen.title)) {
                            TopBarHome()
                        } else {
                            TopBarAddBeers(navController = navController)
                        }
                    },
                    floatingActionButton = {
                        if (shouldShowForScreen(navController = navController, route = Screen.HomeScreen.title)) {
                            FloatingActionButton(onClick = {
                                navController.navigate(Screen.AddBeerScreen.title)
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Add"
                                )
                            }
                        }

                    }
                ) {
                    NavHost(navController = navController, startDestination = Screen.HomeScreen.title) {
                        composable(Screen.HomeScreen.title) {
                            BeersScreen()
                        }
                        composable(Screen.AddBeerScreen.title) {
                            AddBeerScreen {
                                navController.popBackStack()
                            }
                        }
                    }

                }
            }
        }
    }

    @Composable
    private fun TopBarHome() {
        TopAppBar(
            title = {
                Text(Screen.HomeScreen.title)
            }
        )
    }

    @Composable
    private fun TopBarAddBeers(navController: NavController) {
        TopAppBar(
            title = {
                Text(Screen.AddBeerScreen.title)
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Up"
                    )
                }
            }
        )
    }

    @Composable
    fun shouldShowForScreen(navController: NavHostController, route: String): Boolean {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        return route == navBackStackEntry?.destination?.route
    }

}