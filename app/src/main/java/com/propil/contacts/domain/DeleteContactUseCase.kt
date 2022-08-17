package com.propil.contacts.domain

class DeleteContactUseCase(private val contactListRepository: ContactListRepository) {

    fun deleteContact(contact: Contact) {
        contactListRepository.deleteContact(contact)
    }
}