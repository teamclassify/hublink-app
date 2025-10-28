package com.classify.hublink.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.classify.hublink.data.entities.Event
import com.classify.hublink.data.repositories.EventsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EventViewModel(private val repository: EventsRepository) : ViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    var events: StateFlow<List<Event>> = repository
        .allEvents
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    fun addEvent(event: Event) {
        viewModelScope.launch {
            repository.addNewEvent(event)
        }
    }
}