package com.classify.hublink.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.classify.hublink.ui.AppViewModelProvider
import com.classify.hublink.viewmodel.EventViewModel

@Composable
fun MyEventsScreen(
    viewModel: EventViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Surface(
        modifier = Modifier
            .padding(20.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Text(
            text = "List of events i am registered for",
        )

        Spacer(Modifier.height(8.dp))
    }
}