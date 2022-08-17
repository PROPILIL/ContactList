package com.propil.contacts.domain

class GetContactUseCase(private val contactListRepository: ContactListRepository) {

    fun getContact(contactId: Int): Contact {
        return contactListRepository.getContact(contactId)
    }
}