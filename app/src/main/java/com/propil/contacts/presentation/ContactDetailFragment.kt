package com.propil.contacts.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.propil.contacts.databinding.ContactDetailFragmentBinding
import com.propil.contacts.databinding.ContactListFragmentBinding

class ContactDetailFragment: Fragment() {

    private var _binding: ContactDetailFragmentBinding? = null
    private val binding: ContactDetailFragmentBinding
        get() = _binding ?: throw RuntimeException("ContactListFragmentBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ContactDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}