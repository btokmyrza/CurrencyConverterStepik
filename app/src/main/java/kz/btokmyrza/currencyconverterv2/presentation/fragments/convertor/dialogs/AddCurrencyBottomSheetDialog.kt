package kz.btokmyrza.currencyconverterv2.presentation.fragments.convertor.dialogs

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kz.btokmyrza.currencyconverterv2.databinding.DialogBottomSheetAddCurrencyBinding
import kz.btokmyrza.currencyconverterv2.presentation.models.Currency
import kz.btokmyrza.currencyconverterv2.presentation.fragments.convertor.ConvertorViewModel


class AddCurrencyBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: DialogBottomSheetAddCurrencyBinding? = null
    private val binding get() = _binding!!

    private val convertorViewModel: ConvertorViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogBottomSheetAddCurrencyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val spCurrency = binding.spCurrency
        val editCurrencyConversionAmount = binding.tietEditCurrencyConversionAmount

        binding.btnChooseFlag.setOnClickListener {
            convertorViewModel.progressBarVisibility.observe(this) { isProgressVisible ->
                binding.progressBar.isVisible = isProgressVisible
            }

            convertorViewModel.getSelectedCurrencyRateInTenge(spCurrency.selectedItem.toString())
            convertorViewModel.currencyRate.observe(this) {
                editCurrencyConversionAmount.setText(it)
            }

            if (isInternetActive(requireActivity()))
                ChooseFlagBottomSheetDialog().show(childFragmentManager, null)
        }

        binding.btnAddCurrency.setOnClickListener {
            val enteredCurrencyName = spCurrency.selectedItem.toString()
            val enteredCurrencyConversionAmount = editCurrencyConversionAmount.text.toString()
            val chosenFlagUrl = this.requireArguments().getString("CHOSEN_CURRENCY_FLAG_URL")

            val currency = Currency(
                id = convertorViewModel.convertorCurrencies.value?.size ?: 500,
                name = enteredCurrencyName,
                amount = 0,
                conversionRate = 1 / enteredCurrencyConversionAmount.toFloat(),
                flagImageUrl = chosenFlagUrl!!
            )

            convertorViewModel.appendCurrency(currency)
            dismiss()
        }

        return root
    }

    private fun isInternetActive(context: Context): Boolean {
        val noInternetSnackbar = Snackbar.make(
            binding.root,
            "No Internet Connection",
            Snackbar.LENGTH_LONG
        )
        noInternetSnackbar.anchorView = binding.root

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val activeNetwork = connectivityManager.getNetworkCapabilities(network)

        return if (activeNetwork != null) {
            when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> {
                    noInternetSnackbar.show()
                    false
                }
            }
        } else {
            noInternetSnackbar.show()
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}