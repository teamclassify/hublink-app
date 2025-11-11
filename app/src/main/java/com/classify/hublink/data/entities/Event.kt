package com.classify.hublink.data.entities

data class Event(
    val id: String = "",
    val title: String = "",
    val description: String? = "",
    val date: String? = "",
    val time: String? = "",
    val location: String? = "",
    val image: String? = "",
)
