package kz.btokmyrza.currencyconverterv2.presentation.fragments.personal_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kz.btokmyrza.currencyconverterv2.R
import kz.btokmyrza.currencyconverterv2.databinding.FragmentPersonalPageBinding
import kz.btokmyrza.currencyconverterv2.presentation.adapters.PersonalPageSubsectionAdapter

@AndroidEntryPoint
class PersonalPageFragment : Fragment() {

    private var _binding: FragmentPersonalPageBinding? = null
    private val binding get() = _binding!!

    private val personalPageViewModel: PersonalPageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonalPageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mActionBarToolbar = activity?.findViewById<Toolbar>(R.id.action_bar)
        mActionBarToolbar?.title = "Личная страница"


        val imageView: ImageView = binding.imageAvatar
        personalPageViewModel.imageId.observe(viewLifecycleOwner) {
            imageView.id = it
        }

        val personalPageViewPager = binding.viewPager
        val personalPageSubsectionAdapter = PersonalPageSubsectionAdapter(childFragmentManager, lifecycle)
        personalPageViewPager.adapter = personalPageSubsectionAdapter

        TabLayoutMediator(binding.viewPagerTabLayout, personalPageViewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "Основные"
                1 -> tab.text = "Статистика"
                2 -> tab.text = "Еще один таб"
            }
        }.attach()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}