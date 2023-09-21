package com.example.cmpcontacts.contacts.presentation

import com.example.cmpcontacts.contacts.domain.Contact

sealed interface ContactListEvent {
    object OnAddNewContactClick: ContactListEvent
    object DismissContact: ContactListEvent
    object OnAddPhotoClick: ContactListEvent
    object SaveContact: ContactListEvent
    object DeleteContact: ContactListEvent
    data class OnFirstNameChanged(val value: String): ContactListEvent
    data class OnLastNameChanged(val value: String): ContactListEvent
    data class OnEmailChanged(val value: String): ContactListEvent
    data class OnPhoneNumberChanged(val value: String): ContactListEvent
    data class SelectContact(val contact: Contact): ContactListEvent
    data class EditContact(val contact: Contact): ContactListEvent
    class OnPhotoPicked(val bytes: ByteArray): ContactListEvent

}