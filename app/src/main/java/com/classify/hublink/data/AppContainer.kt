package com.classify.hublink.data

import android.content.Context
import com.classify.hublink.data.repositories.EventsRepository
import com.google.firebase.database.FirebaseDatabase


interface AppContainer {
    val eventsRepository: EventsRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val eventsRepository: EventsRepository by lazy {
        EventsRepository(FirebaseDatabase.getInstance())
    }
}