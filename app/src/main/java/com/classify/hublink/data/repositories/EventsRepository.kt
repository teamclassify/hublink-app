package com.classify.hublink.data.repositories

import com.classify.hublink.data.entities.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.childEvents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EventsRepository(private val database: FirebaseDatabase) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _allEvents = MutableStateFlow<List<Event>>(emptyList())
    val allEvents: StateFlow<List<Event>> = _allEvents

    private val _allEnrolledEvents = MutableStateFlow<List<Event>>(emptyList())
    val allEnrolledEvents: StateFlow<List<Event>> = _allEnrolledEvents

    private val eventsRef = database.getReference("events")
    private val usersRef = database.getReference("users").child(auth.currentUser!!.uid)

    init {
        listenForEventChanges()
    }

    private fun listenForEventChanges() {
        eventsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newEvents = mutableListOf<Event>()
                snapshot.children.forEach { childSnapshot ->
                    val event = childSnapshot.getValue(Event::class.java)
                    event?.let { newEvents.add(it) }
                }

                _allEvents.value = newEvents
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error de Base de Datos: ${error.message}")
            }
        })

        usersRef.child("events").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newEvents = mutableListOf<Event>()

                snapshot.children.forEach { childSnapshot ->
                    val event = childSnapshot.getValue(Event::class.java)
                    event?.let { newEvents.add(it) }
                }

                _allEnrolledEvents.value = newEvents
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

    fun enrollToEvent(event: Event) {
        if (auth.currentUser != null) {
            usersRef
                .child("events")
                .push()
                .setValue(event)
        }
    }
}