package com.classify.hublink.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.classify.hublink.ui.AppViewModelProvider
import com.classify.hublink.viewmodel.EventDetailState
import com.classify.hublink.viewmodel.EventViewModel

@Composable
fun EventDetailScreen(
    eventId: String,
    viewModel: EventViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    LaunchedEffect(eventId) {
        viewModel.getEventById(eventId)
    }

    val state by viewModel.eventDetailState.collectAsState()

    Surface(
        modifier = Modifier
            .padding(20.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        when (state) {
            is EventDetailState.Loading -> {
                CircularProgressIndicator()
            }

            is EventDetailState.Error -> {
                Text("Error: ${(state as EventDetailState.Error).message}")
            }

            is EventDetailState.Success -> {
                val event = (state as EventDetailState.Success).event

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    event.description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}