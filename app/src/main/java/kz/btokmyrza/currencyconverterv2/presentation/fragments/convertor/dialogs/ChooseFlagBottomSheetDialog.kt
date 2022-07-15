package kz.btokmyrza.currencyconverterv2.presentation.fragments.convertor.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kz.btokmyrza.currencyconverterv2.databinding.DialogBottomSheetChooseFlagBinding
import kz.btokmyrza.currencyconverterv2.presentation.models.FlagCurrency
import kz.btokmyrza.currencyconverterv2.presentation.adapters.CountryFlagsAdapter
import kz.btokmyrza.currencyconverterv2.util.Constants.COUNTRY_FLAGS_BASE_URL
import kz.btokmyrza.currencyconverterv2.util.Constants.USED_COUNTRY_CODES
import javax.inject.Inject

const val CHOSEN_CURRENCY_FLAG_URL = "CHOSEN_CURRENCY_FLAG_URL"

@AndroidEntryPoint
class ChooseFlagBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: DialogBottomSheetChooseFlagBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var countryFlagsAdapter: CountryFlagsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogBottomSheetChooseFlagBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rvCountryFlags.apply {
            adapter = countryFlagsAdapter
            layoutManager = LinearLayoutManager(root.context)
            itemAnimator = DefaultItemAnimator()
        }

        val countryFlags = mutableListOf<FlagCurrency>()
        USED_COUNTRY_CODES.entries.forEachIndexed { index, (countryCode, countryName) ->
            countryFlags.add(
                FlagCurrency(
                    index,
                    countryName,
                    countryCode,
                    COUNTRY_FLAGS_BASE_URL + countryCode
                )
            )
        }

        countryFlagsAdapter.submitList(countryFlags)

        val chosenCurrencyFlagBundle = Bundle()
        countryFlagsAdapter.setOnFlagCurrencyItemClickListener {
            val chosenCurrencyFlagId = it.flagImageUrl
            chosenCurrencyFlagBundle.putString(CHOSEN_CURRENCY_FLAG_URL, chosenCurrencyFlagId)
            parentFragment?.arguments = chosenCurrencyFlagBundle
            dismiss()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}