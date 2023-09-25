package com.example.cmpcontacts.contacts.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import com.example.cmpcontacts.contacts.domain.Contact
import com.example.cmpcontacts.contacts.presentation.ContactListEvent
import com.example.cmpcontacts.contacts.presentation.ContactListState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ContactListViewModel : ViewModel() {

    private val _state = MutableStateFlow(ContactListState(contacts = mockContacts))
    val state = _state.asStateFlow()
    var newContact: Contact? by mutableStateOf(null)
        private set

    fun onEvent(event: ContactListEvent) {

    }
}

private val mockContacts = (1..50).map {
    Contact(
        id = it.toLong(),
        firstName = "First $it",
        lastName = "Last $it",
        email = "test@test$it.com",
        phoneNumber = "123456789",
        photoBytes = null
    )
}
