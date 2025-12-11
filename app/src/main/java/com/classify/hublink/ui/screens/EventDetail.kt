package com.classify.hublink.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.classify.hublink.ui.AppViewModelProvider
import com.classify.hublink.ui.components.ActionButton
import com.classify.hublink.viewmodel.EventDetailState
import com.classify.hublink.viewmodel.EventViewModel

@OptIn(ExperimentalGlideComposeApi::class)
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
        color = MaterialTheme.colorScheme.surface
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (state) {
                is EventDetailState.Loading -> {
                    CircularProgressIndicator()
                }

                is EventDetailState.Error -> {
                    Text("Error: ${(state as EventDetailState.Error).message}")
                }

                is EventDetailState.Success -> {
                    val event = (state as EventDetailState.Success).event

                    Column(
                        modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())
                    ) {
                        GlideImage(
                            model = event.image ?: "https://www.ecfmg.org/journeysinmedicine/wp-content/uploads/2021/09/IMGevent3_91f3fsmalledit-1024x719.jpeg",
                            contentDescription = "Imagen del evento",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(bottom = 16.dp),
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = event.title,
                            style = MaterialTheme.typography.titleLarge.copy(
                                lineHeight = 32.sp
                            ),

                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        event.description?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    lineHeight = 22.sp
                                ),
                                textAlign = TextAlign.Justify
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        Text(
                            text = "Organizador",
                            style = MaterialTheme.typography.titleMedium.copy(
                                lineHeight = 22.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Column {

                            event.organizerName?.takeIf { it.isNotBlank() }?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        lineHeight = 22.sp
                                    )
                                )
                            }

                            event.organizerEmail?.takeIf { it.isNotBlank() }?.let { email ->
                                Text(
                                    text = email,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        lineHeight = 22.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                            }

                            if (event.organizerName.isNullOrBlank() && event.organizerEmail.isNullOrBlank()) {
                                Text(
                                    text = "Sin información del organizador",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        lineHeight = 22.sp
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Fecha",
                            style = MaterialTheme.typography.titleMedium.copy(
                                lineHeight = 22.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = event.date ?: "Sin fecha",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                lineHeight = 22.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Hora",
                            style = MaterialTheme.typography.titleMedium.copy(
                                lineHeight = 22.sp
                            )
                        )
                        Text(
                            text = formatEventTime(event.time),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                lineHeight = 22.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Lugar",
                            style = MaterialTheme.typography.titleMedium.copy(
                                lineHeight = 22.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = event.location ?: "Sin ubicación",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                lineHeight = 22.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            ActionButton(
                text = "Inscribirse",
                icon = Icons.Default.Add,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = {}
            )
        }
    }
}

fun formatEventTime(rawTime: String?): String {
    if (rawTime.isNullOrBlank()) return "Sin hora"

    val parts = rawTime.split(":")
    val hourInt = parts.getOrNull(0)?.toIntOrNull() ?: return "Sin hora"
    val minuteInt = parts.getOrNull(1)?.toIntOrNull() ?: 0

    val amPm = if (hourInt < 12) "AM" else "PM"

    val hour12 = when {
        hourInt == 0 -> 12
        hourInt > 12 -> hourInt - 12
        else -> hourInt
    }

    val hourFormatted = hour12.toString().padStart(2, '0')
    val minuteFormatted = minuteInt.toString().padStart(2, '0')

    return "$hourFormatted:$minuteFormatted $amPm"
}