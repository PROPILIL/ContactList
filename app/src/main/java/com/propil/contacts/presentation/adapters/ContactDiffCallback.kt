package com.propil.contacts.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.propil.contacts.domain.Contact

class ContactDiffCallback: DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Contact, newItem: Contact): Any? {
        return super.getChangePayload(oldItem, newItem)
    }
}