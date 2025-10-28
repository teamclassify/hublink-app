package com.classify.hublink.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.classify.hublink.data.entities.Event

@Composable
fun EventItem(event: Event) {
    Card {
        Column {
            Box() {
            }

            Row {
                Text(text = event.title)
                event.location?.let { Text(text = it) }
            }
        }
    }
}