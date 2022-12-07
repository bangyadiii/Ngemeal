package com.ngemeal.ngemeal.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ngemeal.ngemeal.model.response.transaction.Transaction
import com.ngemeal.ngemeal.ui.order.inprogress.InProgressFragment
import com.ngemeal.ngemeal.ui.order.pastorders.PastOrdersFragment
import com.ngemeal.ngemeal.ui.profile.account.ProfileAccountFragment
import com.ngemeal.ngemeal.ui.profile.ngemeal.ProfileNgemealFragment

class SectionPagerAdapter (fm: FragmentManager): FragmentPagerAdapter(fm){

    private var inProgressList : ArrayList<Transaction>? = null
    private var pastOrderList : ArrayList<Transaction>? = null

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0-> "In Progress"
            1-> "Past Progress"
            else->""
        }
    }

    override fun getCount(): Int {
        return 2
    }

    fun setData(inProgressList : ArrayList<Transaction>?, pastOrderList : ArrayList<Transaction>?) {
        this.inProgressList = inProgressList
        this.pastOrderList = pastOrderList
    }

    override fun getItem(position: Int): Fragment {
        var fragment : Fragment
        return when(position){
            0->{
                fragment = InProgressFragment()
                val dataBundle =  Bundle()
                dataBundle.putParcelableArrayList("data", inProgressList)
                fragment.arguments = dataBundle
                return fragment
            }
            1->{
                fragment = PastOrdersFragment()
                val dataBundle =  Bundle()
                dataBundle.putParcelableArrayList("data", pastOrderList)
                fragment.arguments = dataBundle
                return fragment
            }
            else-> {
                fragment = InProgressFragment()
                val dataBundle =  Bundle()
                dataBundle.putParcelableArrayList("data", inProgressList)
                fragment.arguments = dataBundle
                return fragment
            }
        }
    }
}