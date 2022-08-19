package com.propil.contacts.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.propil.contacts.R
import com.propil.contacts.domain.Contact

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragmentList()
    }

    private fun initFragmentList() {
        val fragment = ContactListFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_fragment_container, fragment)
            .commit()
    }
}