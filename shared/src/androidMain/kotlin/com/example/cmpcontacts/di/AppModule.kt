package com.example.cmpcontacts.di

import android.content.Context
import com.example.cmpcontacts.contacts.data.SqlDelightContactDataSource
import com.example.cmpcontacts.contacts.domain.ContactDataSource
import com.example.cmpcontacts.core.data.DatabaseDriverFactory
import com.example.cmpcontacts.database.ContactDatabase

actual class AppModule(
    private val context: Context
) {
    actual val contactDataSource: ContactDataSource by lazy {
        SqlDelightContactDataSource(
            db = ContactDatabase(
                driver = DatabaseDriverFactory(context).create()
            )
        )
    }
}