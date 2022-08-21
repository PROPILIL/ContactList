package com.propil.contacts.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.propil.contacts.domain.Contact
import com.propil.contacts.domain.ContactListRepository
import kotlin.random.Random

object ContactListRepositoryImpl : ContactListRepository {


    private val contactList = sortedSetOf<Contact>({ o1, o2 -> o1.name.compareTo(o2.name) })
    private val contactListLiveData = MutableLiveData<List<Contact>>()
    private var incrementId = 0

    init {
        for (i in 0 until 10) {
            val item = Contact(
                "Name $i",
                "Surname $i",
                Random.nextLong(1111111111, 9999999999),
                Contact.URL + Contact.randomNum + Contact.IMAGE_SIZE + Contact.WEB_P
            )
            addContact(item)
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