package com.classify.hublink.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.classify.hublink.ui.AppViewModelProvider
import com.classify.hublink.ui.components.EventItem
import com.classify.hublink.viewmodel.EventViewModel

@Composable
fun MyEventsScreen(
    viewModel: EventViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController
) {
    val enrolledEvents by viewModel.enrolledEvents.collectAsState()

    Surface(
        modifier = Modifier
            .padding(20.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column {
            Text(
                text = "Eventos inscritos",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(12.dp))

            if (enrolledEvents.isEmpty()) {
                Text(
                    text = "Aún no estás inscrito en ningún evento",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    enrolledEvents.forEach { event ->
                        EventItem(
                            event,
                            onClick = { e -> navController.navigate("events/" + e.id) }
                        )
                    }
                }
            }
        }
    }
}