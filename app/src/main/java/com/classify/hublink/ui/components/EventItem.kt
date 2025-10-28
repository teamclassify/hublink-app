package com.classify.hublink.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.classify.hublink.data.entities.Event
import com.classify.hublink.ui.theme.HublinkTheme

@Composable
fun EventItem(event: Event) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(bottom = HublinkTheme.dimens.paddingMedium),
    ) {
        Column {
            Box() {
            }

            Column {
                Text(
                    text = event.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                )

                event.location?.let { Text(text = it) }
            }
        }
    }
}