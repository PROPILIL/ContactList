package com.propil.contacts.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.propil.contacts.databinding.ContactListFragmentBinding
import com.propil.contacts.presentation.adapters.ContactListAdapter

class ContactListFragment : Fragment() {

    private lateinit var contactListAdapter: ContactListAdapter
    private lateinit var viewModel: ContactListViewModel

    private var _binding: ContactListFragmentBinding? = null
    private val binding: ContactListFragmentBinding
        get() = _binding ?: throw RuntimeException("ContactListFragmentBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ContactListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ContactListViewModel::class.java]
        viewModel.contactListLiveData.observe(viewLifecycleOwner) {
            contactListAdapter.submitList(it)
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val recyclerViewContactList = binding.contactListRecycler
        with(recyclerViewContactList) {
            contactListAdapter = ContactListAdapter()
            adapter = contactListAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): ContactListFragment {
            return ContactListFragment()
        }
    }
}