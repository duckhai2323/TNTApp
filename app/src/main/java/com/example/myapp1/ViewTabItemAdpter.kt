package com.example.myapp1

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewTabItemAdpter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> {
                return ConfirmFragment1()
            }

            1 -> {
                return DeliveringFragment1()
            }

            2 -> {
                return ShipedFragment1()
            }

            else -> {
                return ConfirmFragment1()
            }
        }
    }
}