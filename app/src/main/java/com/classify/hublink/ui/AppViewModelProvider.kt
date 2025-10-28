package com.classify.hublink.ui

import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.viewModelFactory
import com.classify.hublink.HublinkApplication
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory

object AppViewModelProvider {
    val Factory = viewModelFactory {
    //        initializer {
    //            TaskViewModel(habitoApplication().container.tasksRepository)
    //        }
    }
}

fun CreationExtras.hublinkApplication(): HublinkApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as HublinkApplication)