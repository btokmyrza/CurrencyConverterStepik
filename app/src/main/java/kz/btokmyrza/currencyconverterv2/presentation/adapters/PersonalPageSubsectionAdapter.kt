package kz.btokmyrza.currencyconverterv2.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import kz.btokmyrza.currencyconverterv2.presentation.fragments.personal_page.subsections.MainsFragment
import kz.btokmyrza.currencyconverterv2.presentation.fragments.personal_page.subsections.OneMoreTabFragment
import kz.btokmyrza.currencyconverterv2.presentation.fragments.personal_page.subsections.StatisticsFragment

class PersonalPageSubsectionAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> MainsFragment()
            1 -> StatisticsFragment()
            2 -> OneMoreTabFragment()
            else -> MainsFragment()
        }
    }

}