package com.example.bleapplication.presentation.screen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.bleapplication.R
import com.example.bleapplication.presentation.screen.details.DeviceDetailsFragment
import com.example.bleapplication.presentation.screen.devices.DevicesFragment
import javax.inject.Inject

class RouterImpl : Router {

    lateinit var activity: MainActivity

    override fun showDevicesFragment() {
        addFragment(DevicesFragment.newInstance())
    }

    override fun showDeviceDetailsFragment() {
        addFragment(DeviceDetailsFragment.newInstance())
    }

    private fun addFragment(fragment: Fragment) {
        activity.supportFragmentManager.inTransaction {
            replace(R.id.fragment_container, fragment, null)
            addToBackStack(fragment.javaClass.name)
        }
    }

    override fun goBack() {
        if (activity.supportFragmentManager.backStackEntryCount > 1) {
            activity.supportFragmentManager.popBackStack()
        } else {
            activity.finish()
        }
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }
}