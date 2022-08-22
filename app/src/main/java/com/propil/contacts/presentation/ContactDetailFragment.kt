package com.propil.contacts.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.propil.contacts.databinding.ContactDetailFragmentBinding
import com.propil.contacts.databinding.ContactListFragmentBinding
import com.propil.contacts.domain.Contact

class ContactDetailFragment: Fragment() {

    private var _binding: ContactDetailFragmentBinding? = null
    private val binding: ContactDetailFragmentBinding
        get() = _binding ?: throw RuntimeException("ContactListFragmentBinding = null")

    private lateinit var viewModel: ContactDetailViewModel

    private var contactId = Contact.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ContactDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ContactDetailViewModel::class.java]
        launchEditMode()
    }

    private fun launchEditMode() {
        viewModel.getContact(contactId)
        viewModel.contact.observe(viewLifecycleOwner) {
            binding.nameFieldEditText.setText(it.name)
            binding.surnameFieldEditText.setText(it.surname)
            binding.phoneFieldEditText.setText((it.phoneNumber).toString())
            binding.contactPhoto.load(it.photo)
        }
        binding.saveButton.setOnClickListener {
            viewModel.editContact(
                binding.nameFieldEditText.text?.toString(),
                binding.surnameFieldEditText.text?.toString(),
                binding.phoneFieldEditText.text?.toString()
            )
            viewModel.isWorkFinished.observe(viewLifecycleOwner) {
                activity?.onBackPressed()
            }
        }
    }

    private fun parseArgs(){
        val args = requireArguments()
        if(!args.containsKey(CONTACT_ID)) {
            throw RuntimeException("Argument contact_id is absent")
        } else {
            contactId = args.getInt(CONTACT_ID, Contact.UNDEFINED_ID)

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val CONTACT_ID = "CONTACT_ID"

        fun newInstanceEdit(contactId: Int): ContactDetailFragment {
            return ContactDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(CONTACT_ID, contactId)
                }
            }
        }
    }
}