package com.propil.contacts.domain

class AddContactUseCase(private val contactListRepository: ContactListRepository) {

    fun addContact(contact: Contact) {
        contactListRepository.addContact(contact)
    }
}