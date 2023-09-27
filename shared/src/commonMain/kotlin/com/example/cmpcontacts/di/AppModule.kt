package com.example.cmpcontacts.di

import com.example.cmpcontacts.contacts.domain.ContactDataSource

expect class AppModule {
    val contactDataSource: ContactDataSource
}