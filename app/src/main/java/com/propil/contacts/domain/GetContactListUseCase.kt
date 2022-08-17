package com.propil.contacts.domain

import androidx.lifecycle.LiveData

class GetContactListUseCase(private val contactListRepository: ContactListRepository) {

    fun getContactList(): LiveData<List<Contact>> {
        return contactListRepository.getContactList()
    }
}