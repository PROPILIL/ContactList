package com.propil.contacts.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.propil.contacts.databinding.ContactItemBinding
import com.propil.contacts.domain.Contact
import java.util.*

class ContactListAdapter :
    ListAdapter<Contact, ContactListAdapter.ContactViewHolder>(ContactDiffCallback()) {

    var onContactClick: ((Contact) -> Unit)? = null
    var onLongContactClick: ((Contact) -> Unit)? = null
    private var unfilteredList = currentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        with(holder) {
            with(getItem(position)) {
                binding.contactName.text = this.name
                binding.contactSurname.text = this.surname
                binding.contactPhone.text = this.phoneNumber.toString()
                binding.contactPhoto.load(this.photo)
            }
        }
        modifyList(currentList)
    }


    inner class ContactViewHolder(val binding: ContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onContactClick?.invoke(getItem(adapterPosition))
            }
            binding.root.setOnLongClickListener {
                onLongContactClick?.invoke(getItem(adapterPosition))
                true
            }
         }
    }

    private fun modifyList(list: List<Contact>) {
        unfilteredList = list
        submitList(list)
    }

    fun filter(query: CharSequence?) {
        val list = mutableListOf<Contact>()

        if(!query.isNullOrEmpty()){
            list.addAll(unfilteredList.filter {
                it.name
                    .lowercase(Locale.getDefault())
                    .contains(query
                        .toString()
                        .lowercase(Locale.getDefault())) ||
                it.surname
                    .lowercase(Locale.getDefault())
                    .contains(query
                        .toString()
                        .lowercase(Locale.getDefault()))
            })
        } else {
            list.addAll(unfilteredList)
        }
        submitList(list)
    }


}