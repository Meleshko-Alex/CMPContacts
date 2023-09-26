package com.example.cmpcontacts.contacts.data

import com.example.cmpcontacts.contacts.domain.Contact
import database.ContactEntity

fun ContactEntity.toContact(): Contact {
    return Contact(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        photoBytes = null // TODO: Get the image
    )
}