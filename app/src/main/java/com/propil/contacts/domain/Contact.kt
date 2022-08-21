package com.propil.contacts.domain

import kotlin.random.Random

data class Contact(
    val name: String,
    val surname: String,
    val phoneNumber: Long,
    val photo: String = "",
    var id: Int = UNDEFINED_ID
) {

    companion object {
        const val UNDEFINED_ID = -1
        const val URL = "https://picsum.photos/id/"
        const val IMAGE_SIZE = "/100"
        const val WEB_P = ".webp"

        var randomNum = (Random.nextInt(1, 100)).toString()

    }
}