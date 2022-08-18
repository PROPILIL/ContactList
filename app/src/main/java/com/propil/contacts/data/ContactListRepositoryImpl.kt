package com.propil.contacts.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.propil.contacts.domain.Contact
import com.propil.contacts.domain.ContactListRepository
import kotlin.random.Random

class ContactListRepositoryImpl : ContactListRepository {


    private val contactList = sortedSetOf<Contact>({ o1, o2 -> o1.id.compareTo(o2.id) })
    private val contactListLiveData = MutableLiveData<List<Contact>>()
    private var incrementId = 0

    init {
        for (i in 0 until 1000) {
            val item = Contact(
                "Name $i",
                "Surname $i",
                "Phone ${Random.nextLong(1111111111, 9999999999)}",
                Random.nextInt(0, 1)
            )
        }
    }


    override fun getContactList(): LiveData<List<Contact>> {
        return contactListLiveData
    }

    override fun getContact(contactId: Int): Contact {
        return contactList.find {
            it.id == contactId
        } ?: throw RuntimeException("Contact with id $contactId not found")
    }

    override fun addContact(contact: Contact) {
        if (contact.id == Contact.UNDEFINED_ID) {
            contact.id = incrementId++
        }
        contactList.add(contact)
        updateList()
    }

    override fun editContact(contact: Contact) {
        val oldContact = getContact(contact.id)
        contactList.remove(oldContact)
        addContact(contact)
    }

    override fun deleteContact(contact: Contact) {
        contactList.remove(contact)
        updateList()
    }

    private fun updateList() {
        contactListLiveData.value = contactList.toList()
    }
}