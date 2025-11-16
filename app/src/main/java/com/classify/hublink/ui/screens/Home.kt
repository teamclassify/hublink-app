package com.classify.hublink.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.classify.hublink.ui.components.ListOfEvents
import com.classify.hublink.viewmodel.AuthViewModel

@Composable
fun HomeScreen(authViewModel: AuthViewModel = viewModel(), navController: NavHostController) {
    Surface(
        modifier = Modifier
            .padding(20.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        ListOfEvents(navController = navController)
    }
}