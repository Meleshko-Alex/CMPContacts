package com.example.cmpcontacts.contacts.data

import com.example.cmpcontacts.contacts.domain.Contact
import com.example.cmpcontacts.core.data.ImageStorage
import database.ContactEntity

suspend fun ContactEntity.toContact(imageStorage: ImageStorage): Contact {
    return Contact(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        photoBytes = imagePath?.let { imageStorage.getImage(it)}
    )
}