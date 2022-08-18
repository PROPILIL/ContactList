package com.propil.contacts.presentation

import androidx.lifecycle.ViewModel
import com.propil.contacts.data.ContactListRepositoryImpl // no time - no DI
import com.propil.contacts.domain.*

class ContactListViewModel: ViewModel() {

    private val repository = ContactListRepositoryImpl


    private val getContactListUseCase = GetContactListUseCase(repository)
    private val deleteContactUseCase = DeleteContactUseCase(repository)

    val contactListLiveData = getContactListUseCase.getContactList()


    fun deleteContact(contact: Contact) {
        deleteContactUseCase.deleteContact(contact)
    }
}