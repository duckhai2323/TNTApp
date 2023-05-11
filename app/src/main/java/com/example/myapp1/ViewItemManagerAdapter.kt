package com.example.myapp1

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewItemManagerAdapter(fragmentActivity:FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0->{
                return ProductDisplayFragment()
            }
            1->{
                return ConfirmFragment2()
            }
            2->{
                return DeliveringFragment2()
            }
            3->{
                return ShipedFragment2()
            }
            else->{
                return ProductDisplayFragment()
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return when(position) {
            0 -> 0
            1 -> 1
            2 -> 2
            3->3
            else -> 0
        }
    }
}