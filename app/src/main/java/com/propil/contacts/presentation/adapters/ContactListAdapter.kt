package com.propil.contacts.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.propil.contacts.databinding.ContactItemBinding
import com.propil.contacts.domain.Contact
import com.propil.contacts.presentation.adapters.ContactViewHolder

class ContactListAdapter: ListAdapter<Contact, ContactViewHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        with(holder) {
            with(getItem(position)) {
                binding.contactName.text = this.name
                binding.contactSurname.text = this.surname
                binding.contactPhone.text = this.phoneNumber
            }
        }
    }

    // This fun is not necessary because of ListAdapter
//    override fun getItemCount(): Int {
//        return contactList.size
//    }
}