package com.propil.contacts.domain

import androidx.lifecycle.LiveData

interface ContactListRepository {

    fun getContactList(): LiveData<List<Contact>>

    fun getContact(contactId: Int): Contact

    fun addContact(contact: Contact)

    fun editContact(contact: Contact)

    fun deleteContact(contact: Contact)
}