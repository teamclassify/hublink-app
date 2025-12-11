package com.classify.hublink.data.entities

data class Event(
    var id: String = "",
    val title: String = "",
    val description: String? = "",
    val date: String? = "",
    val time: String? = "",
    val location: String? = "",
    val image: String? = "",
    var organizerId: String = "",
    var organizerName: String = "",
    var organizerEmail: String = ""
)
