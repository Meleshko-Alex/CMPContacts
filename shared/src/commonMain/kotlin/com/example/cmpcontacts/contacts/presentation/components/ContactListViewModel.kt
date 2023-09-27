package com.example.cmpcontacts.contacts.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import com.example.cmpcontacts.contacts.domain.Contact
import com.example.cmpcontacts.contacts.domain.ContactDataSource
import com.example.cmpcontacts.contacts.domain.ContactValidator
import com.example.cmpcontacts.contacts.presentation.ContactListEvent
import com.example.cmpcontacts.contacts.presentation.ContactListState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactListViewModel(
    private val contactDataSource: ContactDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(ContactListState())
    val state = combine(
        _state,
        contactDataSource.getContacts(),
        contactDataSource.getRecentContacts(20)
    ) { state, contacts, resentContacts ->
        state.copy(
            contacts = contacts,
            recentlyAddedContacts = resentContacts
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ContactListState())
    var newContact: Contact? by mutableStateOf(null)
        private set

    fun onEvent(event: ContactListEvent) {
        when (event) {
            ContactListEvent.DeleteContact -> { deleteContact() }
            ContactListEvent.DismissContact -> { dismissContact() }
            is ContactListEvent.EditContact -> { editContact(event) }
            ContactListEvent.OnAddNewContactClick -> { addNewContact() }
            is ContactListEvent.OnEmailChanged -> { changeEmail(event) }
            is ContactListEvent.OnFirstNameChanged -> { changeFirstName(event) }
            is ContactListEvent.OnLastNameChanged -> { changeLastName(event) }
            is ContactListEvent.OnPhoneNumberChanged -> { changePhoneNumber(event) }
            is ContactListEvent.OnPhotoPicked -> { changePhoto(event) }
            ContactListEvent.SaveContact -> { saveContact() }
            is ContactListEvent.SelectContact -> { selectContact(event) }
            else -> Unit
        }
    }

    private fun saveContact() {
        newContact?.let { contact ->
            val result = ContactValidator.validateContact(contact)
            val errors = listOfNotNull(
                result.firstNameError,
                result.lastNameError,
                result.emailError,
                result.phoneNumberError,
            )

            if (errors.isEmpty()) {
                _state.update {
                    it.copy(
                        isAddContactSheetOpen = false,
                        firstNameError = null,
                        lastNameError = null,
                        emailError = null,
                        phoneNumberError = null
                    )
                }
                viewModelScope.launch {
                    contactDataSource.insertContact(contact)
                    delay(300L) //Animation delay
                    newContact = null
                }
            } else {
                _state.update {
                    it.copy(
                        firstNameError = result.firstNameError,
                        lastNameError = result.lastNameError,
                        emailError = result.emailError,
                        phoneNumberError = result.phoneNumberError
                    )
                }
            }
        }
    }

    private fun selectContact(event: ContactListEvent.SelectContact) {
        _state.update {
            it.copy(
                selectedContact = event.contact,
                isSelectedContactSheetOpen = true
            )
        }
    }

    private fun changePhoto(event: ContactListEvent.OnPhotoPicked) {
        newContact = newContact?.copy(
            photoBytes = event.bytes
        )
    }

    private fun changePhoneNumber(event: ContactListEvent.OnPhoneNumberChanged) {
        newContact = newContact?.copy(
            phoneNumber = event.value
        )
    }

    private fun changeLastName(event: ContactListEvent.OnLastNameChanged) {
        newContact = newContact?.copy(
            lastName = event.value
        )
    }

    private fun changeFirstName(event: ContactListEvent.OnFirstNameChanged) {
        newContact = newContact?.copy(
            firstName = event.value
        )
    }

    private fun changeEmail(event: ContactListEvent.OnEmailChanged) {
        newContact = newContact?.copy(
            email = event.value
        )
    }

    private fun addNewContact() {
        _state.update {
            it.copy(
                isAddContactSheetOpen = true
            )
        }
        newContact = Contact(
            id = null,
            firstName = "",
            lastName = "",
            email = "",
            phoneNumber = "",
            photoBytes = null
        )
    }

    private fun editContact(event: ContactListEvent.EditContact) {
        _state.update {
            it.copy(
                selectedContact = null,
                isAddContactSheetOpen = true,
                isSelectedContactSheetOpen = false
            )
        }
        newContact = event.contact
    }

    private fun dismissContact() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isSelectedContactSheetOpen = false,
                    isAddContactSheetOpen = false,
                    firstNameError = null,
                    lastNameError = null,
                    emailError = null,
                    phoneNumberError = null
                )
            }
            delay(300L) //Animation delay
            newContact = null
            _state.update {
                it.copy(
                    selectedContact = null
                )
            }
        }
    }

    private fun deleteContact() {
        viewModelScope.launch {
            _state.value.selectedContact?.id?.let { id ->
                _state.update {
                    it.copy(
                        isSelectedContactSheetOpen = false
                    )
                }
                contactDataSource.deleteContact(id)
                delay(300L) //Animation delay
                _state.update {
                    it.copy(
                        selectedContact = null
                    )
                }
            }
        }
    }
}
