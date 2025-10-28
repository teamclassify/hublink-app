package com.classify.hublink.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.classify.hublink.ui.AppViewModelProvider
import com.classify.hublink.viewmodel.EventViewModel

@Composable
fun ListOfEvents(
    viewModel: EventViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val events by viewModel.events.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        events.forEach { event ->
            EventItem(
                event
            )

            HorizontalDivider(
                modifier = Modifier.height(1.dp).fillMaxWidth(),
                thickness = DividerDefaults.Thickness, color = MaterialTheme.colorScheme.outline
            )
        }
    }
}