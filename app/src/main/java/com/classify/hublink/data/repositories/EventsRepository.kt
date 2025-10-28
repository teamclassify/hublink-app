package com.classify.hublink.data.repositories

import com.classify.hublink.data.entities.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EventsRepository(private val database: FirebaseDatabase) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _allEvents = MutableStateFlow<List<Event>>(emptyList())
    val allEvents: StateFlow<List<Event>> = _allEvents

    private val eventsRef = database.getReference("events")

    init {
        listenForEventChanges()
    }

    private fun listenForEventChanges() {
        eventsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newEvents = mutableListOf<Event>()
                snapshot.children.forEach { childSnapshot ->
                    val habit = childSnapshot.getValue(Event::class.java)
                    habit?.let { newEvents.add(it) }
                }

                _allEvents.value = newEvents
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error de Base de Datos: ${error.message}")
            }
        })
    }

    fun addNewEvent(event: Event) {
        if (auth.currentUser != null) {
            eventsRef
                .push()
                .setValue(event)
        }
    }
}