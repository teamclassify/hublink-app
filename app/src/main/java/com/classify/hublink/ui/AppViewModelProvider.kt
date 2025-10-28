package com.classify.hublink.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.classify.hublink.HublinkApplication
import com.classify.hublink.viewmodel.EventViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
            initializer {
                EventViewModel(hublinkApplication().container.eventsRepository)
            }
    }
}

fun CreationExtras.hublinkApplication(): HublinkApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as HublinkApplication)