package com.example.finalproject_creativebaz

data class UserExtraInfo(val userId: String, val profession: String, val bio: String, val contact: String) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
    constructor(): this("","","", "")
}