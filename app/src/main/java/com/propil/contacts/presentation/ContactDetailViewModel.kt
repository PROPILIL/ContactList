package com.propil.contacts.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.propil.contacts.data.ContactListRepositoryImpl
import com.propil.contacts.domain.AddContactUseCase
import com.propil.contacts.domain.Contact
import com.propil.contacts.domain.EditContactUseCase
import com.propil.contacts.domain.GetContactUseCase
import java.lang.Exception

class ContactDetailViewModel : ViewModel() {

    private val repository = ContactListRepositoryImpl

    private val addContactUseCase = AddContactUseCase(repository)
    private val getContactUseCase = GetContactUseCase(repository)
    private val editContactUseCase = EditContactUseCase(repository)

    //TODO: Have to know why this thing here
    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = _contact

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputSurname = MutableLiveData<Boolean>()
    val errorInputSurname: LiveData<Boolean>
        get() = _errorInputSurname

    private val _errorInputPhone = MutableLiveData<Boolean>()
    val errorInputPhone: LiveData<Boolean>
        get() = _errorInputPhone

    private val _isWorkFinished = MutableLiveData<Unit>()
    val isWorkFinished: LiveData<Unit>
        get() = _isWorkFinished

    fun addContact(inputName: String?, inputSurname: String?, inputPhone: String?) {
        val name = parseNames(inputName)
        val surname = parseNames(inputSurname)
        val phone = parsePhone(inputPhone)
        val fieldsValid = validateInput(name, surname, phone)

        if (fieldsValid) {
            val item = Contact(name, surname, phone)
            addContactUseCase.addContact(item)
            finishWork()
        }
    }

    fun editContact(inputName: String?, inputSurname: String?, inputPhone: String?) {
        val name = parseNames(inputName)
        val surname = parseNames(inputSurname)
        val phone = parsePhone(inputPhone)
        val fieldsValid = validateInput(name, surname, phone)

        if (fieldsValid) {
            _contact.value?.let {
                val item = it.copy(name = name, surname = surname, phoneNumber = phone)
                editContactUseCase.editContact(item)
                finishWork()
            }
        }
    }

    fun getContact(contactId: Int) {
        val item = getContactUseCase.getContact(contactId)
        _contact.value = item
    }

    private fun parsePhone(inputPhone: String?): Long {
        return try {
            inputPhone?.trim()?.toLong() ?: UNDEFINED_PHONE
        } catch (e: Exception) {
            UNDEFINED_PHONE
        }
    }

    private fun validateInput(
        inputName: String,
        inputSurname: String,
        inputPhone: Long
    ): Boolean {
        var result = true
        if(inputName.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if(inputSurname.isBlank()) {
            _errorInputSurname.value = true
            result = false
        }
        if(inputPhone <= 0) {
            _errorInputPhone.value = true
            result = false
        }
        return result
    }

    private fun parseNames(inputNames: String?): String {
        return inputNames?.trim() ?: ""

    }

    private fun finishWork() {
        _isWorkFinished.value = Unit
    }

    companion object {
        private const val UNDEFINED_PHONE: Long = 0
    }

}