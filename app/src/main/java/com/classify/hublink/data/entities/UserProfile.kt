package com.classify.hublink.data.entities

data class UserProfile(
    var id: String = "",
    var email: String = "",
    var name: String = "",
    var phone: String = "",
    var university: String = "",
    var career: String = "",
    var interests: String = "",
    var linkedin: String = "",
    var github: String = "",
    var photoUrl: String = ""
)