package kz.btokmyrza.currencyconverterv2.presentation.fragments.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.*
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kz.btokmyrza.currencyconverterv2.R
import kz.btokmyrza.currencyconverterv2.databinding.FragmentSearchBinding
import kz.btokmyrza.currencyconverterv2.presentation.models.SearchCurrency
import kz.btokmyrza.currencyconverterv2.presentation.adapters.SearchCurrencyAdapter
import kz.btokmyrza.currencyconverterv2.util.Constants.INITIAL_SEARCH_CURRENCY_LIST

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels()

    lateinit var searchCurrencyAdapter: SearchCurrencyAdapter
    private var searchHistoryChips = mutableSetOf<String>()
    private var initialSearchCurrencyList: List<SearchCurrency> = INITIAL_SEARCH_CURRENCY_LIST


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mActionBarToolbar = activity?.findViewById<Toolbar>(R.id.action_bar)
        mActionBarToolbar?.title = "Search"

        setupSearchCurrencyAdapter()

        searchViewModel.searchCurrencies.observe(viewLifecycleOwner) {
            searchCurrencyAdapter.differ.submitList(it)
        }

        searchCurrencyAdapter.setOnItemClickListener {
            if (!searchHistoryChips.contains(it.name))
                createSearchHistoryChips(it.name)
        }

        binding.tietCurrencySearch.addTextChangedListener { s ->
            val enteredCurrencyName = s.toString()
            val foundItems = mutableListOf<SearchCurrency>()
            initialSearchCurrencyList.forEach {
                if (it.name.contains(enteredCurrencyName, ignoreCase = true)) {
                    foundItems.add(it)
                }
                else if (enteredCurrencyName.isBlank()) {
                    searchCurrencyAdapter.differ.submitList(initialSearchCurrencyList)
                }
            }
            searchCurrencyAdapter.differ.submitList(foundItems)
        }

        crashApp()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun crashApp() {
        throw RuntimeException("Test Crash") // Force a crash
    }

    private fun setupSearchCurrencyAdapter() {
        searchCurrencyAdapter = SearchCurrencyAdapter()
        binding.rvSearchCurrencies.apply {
            adapter = searchCurrencyAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun createSearchHistoryChips(searchChipText: String) {
        val searchHistoryChipGroup = binding.chipGroupSearchHistory
        (layoutInflater.inflate(R.layout.chip_choice, searchHistoryChipGroup, false) as? Chip)?.let { chip ->
            chip.id = View.generateViewId()
            chip.text = searchChipText
            chip.setOnCheckedChangeListener { currentChip, isChecked ->
                if (isChecked) {
                    searchHistoryChipGroup.check(currentChip.id)
                    val chipPressedList = initialSearchCurrencyList.filter {
                        it.name == searchChipText
                    }
                    searchCurrencyAdapter.differ.submitList(chipPressedList)
                } else {
                    searchHistoryChipGroup.clearCheck()
                    searchCurrencyAdapter.differ.submitList(initialSearchCurrencyList)
                }
            }
            searchHistoryChipGroup.addView(chip)
            searchHistoryChips.add(searchChipText)
        }
    }
}