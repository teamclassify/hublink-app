package com.classify.hublink.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NotificationAdd
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.classify.hublink.ui.screens.EventDetailScreen
import com.classify.hublink.ui.screens.HomeScreen
import com.classify.hublink.ui.screens.MyEventsScreen
import com.classify.hublink.ui.screens.NewEventScreen
import com.classify.hublink.ui.screens.ProfileScreen

enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String,
    val visible: Boolean
) {
    HOME("home", "Home", Icons.Default.Home, "Home", true),
    MY_EVENTS("my-events", "My Events", Icons.Default.CalendarMonth, "My Events", true),
    NEW_EVENT("new-event", "New Event", Icons.Default.NotificationAdd, "New Event", true),
    EVENT_DETAIL("events/{eventId}", "Event Detail", Icons.Default.NotificationAdd, "Event Detail", false),
    PROFILE("profile", "Profile", Icons.Default.Person, "Profile", true),
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ViewModelConstructorInComposable", "UnusedMaterial3ScaffoldPaddingParameter")
@Preview()
@Composable
fun NavigationBar() {
    val navController = rememberNavController()
    val startDestination = Destination.HOME
    var selectedDestination by rememberSaveable { mutableStateOf(startDestination.route) }

    Scaffold(
        topBar = {
            DynamicTopBar(navController = navController)
        },
        bottomBar = {
            Column() {
                HorizontalDivider(
                    modifier = Modifier.height(1.dp).fillMaxWidth(),
                    thickness = DividerDefaults.Thickness, color = MaterialTheme.colorScheme.outline
                )

                NavigationBar(
                    windowInsets = NavigationBarDefaults.windowInsets,
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    Destination.entries.forEach { destination ->
                        if (destination.visible) {
                            NavigationBarItem(
                                selected = selectedDestination == destination.route,
                                onClick = {
                                    navController.navigate(route = destination.route)
                                    selectedDestination = destination.route
                                },
                                icon = {
                                    Icon(
                                        destination.icon,
                                        contentDescription = destination.contentDescription
                                    )
                                },
                                label = { Text(destination.label) },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    selectedIconColor = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }
                }
            }
        }
    ) {
            innerPadding ->

        NavHost(navController = navController, startDestination = Destination.HOME.route, modifier = Modifier.padding(innerPadding)) {
            composable(route = Destination.HOME.route) {
                HomeScreen(navController = navController)
            }
            composable(route = Destination.PROFILE.route) {
                ProfileScreen()
            }
            composable(route = Destination.MY_EVENTS.route) {
                MyEventsScreen(navController = navController)
            }
            composable(route = Destination.NEW_EVENT.route) {
                NewEventScreen(navController = navController)
            }
            composable(
                route = Destination.EVENT_DETAIL.route,
                arguments = listOf(navArgument("eventId") { type = NavType.StringType })
            ) { backStackEntry ->
                val eventId = backStackEntry.arguments?.getString("eventId")

                if (eventId != null) {
                    EventDetailScreen(
                        eventId = eventId,
                    )
                } else {
                    // Manejar el caso de error (e.g., mostrar un mensaje)
                }
            }
        }
    }
}
