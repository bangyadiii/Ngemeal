package com.ngemeal.ngemeal.ui.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ngemeal.ngemeal.ui.home.newtaste.HomeNewTasteFragment
import com.ngemeal.ngemeal.ui.home.popular.HomePopularFragment
import com.ngemeal.ngemeal.ui.home.recommended.HomeRecommendedFragment
import com.ngemeal.ngemeal.ui.profile.account.ProfileAccountFragment
import com.ngemeal.ngemeal.ui.profile.ngemeal.ProfileNgemealFragment

class SectionPagerAdapter (fm: FragmentManager): FragmentPagerAdapter(fm){

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0-> "Akun"
            1-> "Ngemeal"
            else->""
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        var fragment : Fragment
        return when(position){
            0->{
                fragment = ProfileAccountFragment()
                return fragment
            }
            1->{
                fragment = ProfileNgemealFragment()
                return fragment
            }
            else-> {
                fragment = ProfileAccountFragment()
                return fragment
            }
        }
    }
}