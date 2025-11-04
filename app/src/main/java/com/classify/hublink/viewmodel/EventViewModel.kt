package com.classify.hublink.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.classify.hublink.config.supabase
import com.classify.hublink.data.entities.Event
import com.classify.hublink.data.repositories.EventsRepository
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.util.UUID

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

    fun enrollToEvent(event: Event) {
        viewModelScope.launch {
            repository.enrollToEvent(event)
        }
    }


    suspend fun uploadImageToSupabase(
        context: Context,
        imageUri: Uri
    ): String? {
        val bucket = supabase.storage.from("events")

        val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
        val bytes = inputStream?.readBytes() ?: return null
        inputStream.close()

        val fileName = "public/event_${UUID.randomUUID()}.jpg"

        return withContext(Dispatchers.IO) {
            try {
                bucket.upload(fileName, bytes)
                bucket.publicUrl(fileName)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}