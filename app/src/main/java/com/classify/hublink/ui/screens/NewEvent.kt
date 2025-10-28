package com.classify.hublink.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.classify.hublink.data.entities.Event
import com.classify.hublink.ui.AppViewModelProvider
import com.classify.hublink.ui.components.CustomButton
import com.classify.hublink.ui.components.Destination
import com.classify.hublink.ui.theme.HublinkTheme
import com.classify.hublink.viewmodel.EventViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEventScreen(
    viewModel: EventViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController
) {
    val scope = rememberCoroutineScope()

    var isDialogTimeOpen by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false,
    )

    Surface(
        modifier = Modifier.padding(20.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = HublinkTheme.dimens.paddingMedium),
                value = title,
                onValueChange = { title = it },
                label = { Text("Event title") }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = HublinkTheme.dimens.paddingMedium),
                value = location,
                onValueChange = { location = it },
                label = { Text("Event location") }
            )


            Text(
                modifier = Modifier.padding(bottom = HublinkTheme.dimens.paddingNormal),
                text = "Time",
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = selectedDate,
                onValueChange = { },
                label = { Text("Date") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = !showDatePicker }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select date"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = HublinkTheme.dimens.paddingMedium)
                    .height(64.dp)
            )


            Column (
                modifier = Modifier.clickable {
                    isDialogTimeOpen = true
                }
                    .padding(bottom = HublinkTheme.dimens.paddingNormal)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(HublinkTheme.dimens.buttonHeightNormal)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(HublinkTheme.dimens.roundedShapeNormal)
                        )
                        .padding(HublinkTheme.dimens.paddingMedium),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Time",
                    )
                    Text(
                        text = "" + timePickerState.hour + ":" + timePickerState.minute,
                    )
                }
            }

            if (isDialogTimeOpen) {
                AlertDialog(
                    text = {
                        TimePicker(
                            state = timePickerState,
                        )
                    },
                    onDismissRequest = {
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                isDialogTimeOpen = false
                            }
                        ) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                isDialogTimeOpen = false
                            }
                        ) {
                            Text("Dismiss")
                        }
                    }
                )
            }

            if (showDatePicker) {
                AlertDialog(
                    text = {
                        DatePicker(
                            state = datePickerState,
                            showModeToggle = true
                        )
                    },
                    onDismissRequest = {
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDatePicker = false
                            }
                        ) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDatePicker = false
                            }
                        ) {
                            Text("Dismiss")
                        }
                    }
                )
            }

//            Text(
//                modifier = Modifier.padding(bottom = HublinkTheme.dimens.paddingNormal),
//                text = "Customize",
//                fontWeight = FontWeight.Bold
//            )

            Column(
                modifier = Modifier.padding(top = HublinkTheme.dimens.paddingNormal)
            ) {
                CustomButton(
                    onTap = {
                        viewModel.addEvent(
                            Event(
                                title = title,
                                location = location,
                                date = selectedDate,
                                time = "" + timePickerState.hour + ":" + timePickerState.minute
                            )
                        )

                        navController.navigate(route = Destination.HOME.route)
                    },
                    text = "Save",
                    textColor = MaterialTheme.colorScheme.surface,
                    buttonColor = MaterialTheme.colorScheme.primary,
                )
            }

        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(Date(millis))
}

