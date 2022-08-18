package com.propil.contacts.domain

data class Contact(
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val sex: Int,
    var id: Int = UNDEFINED_ID
) {

    companion object {
        const val UNDEFINED_ID = -1
        const val MALE = 0
        const val FEMALE = 1
    }
}