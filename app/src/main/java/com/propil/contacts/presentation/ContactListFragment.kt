package com.propil.contacts.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.propil.contacts.R
import com.propil.contacts.databinding.ActivityMainBinding
import com.propil.contacts.databinding.ContactListFragmentBinding
import com.propil.contacts.presentation.adapters.ContactListAdapter

class ContactListFragment : Fragment() {

    private lateinit var contactListAdapter: ContactListAdapter
    private lateinit var viewModel: ContactListViewModel
    private var _activityBinding: ActivityMainBinding? = null
    private val activityBinding: ActivityMainBinding
        get() = _activityBinding ?: throw RuntimeException("ActivityMainBinding = null")

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
        setupClickListener()
        setupLongClickListener()
    }

    private fun setupClickListener() {
        contactListAdapter.onContactClick = {
            launchFragment(ContactDetailFragment.newInstanceEdit(it.id))
        }
    }

    private fun setupLongClickListener() {
        contactListAdapter.onContactClick = {
            viewModel.deleteContact(it)
        }

    }

    private fun launchFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _activityBinding = null
    }

    companion object {


        fun newInstance(): ContactListFragment {
            return ContactListFragment()
        }

    }
}