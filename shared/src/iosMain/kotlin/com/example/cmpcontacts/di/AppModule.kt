package com.example.cmpcontacts.di

import com.example.cmpcontacts.contacts.data.SqlDelightContactDataSource
import com.example.cmpcontacts.contacts.domain.ContactDataSource
import com.example.cmpcontacts.core.data.DatabaseDriverFactory
import com.example.cmpcontacts.core.data.ImageStorage
import com.example.cmpcontacts.database.ContactDatabase

actual class AppModule {
    actual val contactDataSource: ContactDataSource by lazy {
        SqlDelightContactDataSource(
            db = ContactDatabase(
                driver = DatabaseDriverFactory().create()
            ),
            imageStorage = ImageStorage()
        )
    }
}