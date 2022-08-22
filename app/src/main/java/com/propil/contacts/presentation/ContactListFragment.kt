package com.propil.contacts.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.propil.contacts.R
import com.propil.contacts.databinding.ActivityMainBinding
import com.propil.contacts.databinding.ContactListFragmentBinding
import com.propil.contacts.domain.Contact
import com.propil.contacts.presentation.adapters.ContactListAdapter

class ContactListFragment : Fragment() {

    private lateinit var contactListAdapter: ContactListAdapter
    private lateinit var viewModel: ContactListViewModel

    private var _binding: ContactListFragmentBinding? = null
    private val binding: ContactListFragmentBinding
        get() = _binding ?: throw RuntimeException("ContactListFragmentBinding = null")

    private var contactDetailContainer: FragmentContainerView? = null

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
        contactDetailContainer = binding.contactDetailContainer
        viewModel = ViewModelProvider(this)[ContactListViewModel::class.java]
        viewModel.contactListLiveData.observe(viewLifecycleOwner) {
            contactListAdapter.submitList(it)
        }
        setupRecyclerView()
        setUpSearch()
    }

    private fun isPortraitMode(): Boolean{
        return contactDetailContainer == null
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
            if(isPortraitMode()) {
                launchFragment(ContactDetailFragment.newInstanceEdit(it.id))
            } else {
                launchFragmentLand(ContactDetailFragment.newInstanceEdit(it.id))
            }
        }
    }

    private fun setupLongClickListener() {
        contactListAdapter.onLongContactClick = {
            deleteAlert(it)
        }

    }

    private fun setUpSearch(){
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                contactListAdapter.filter(newText)
                return true
            }
        })
    }

    private fun launchFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun launchFragmentLand(fragment: Fragment) {
        requireActivity().supportFragmentManager.popBackStack()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.contact_detail_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun deleteAlert(contact : Contact){
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Wanna delete contact?")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteContact(contact)
            }
            .show()
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