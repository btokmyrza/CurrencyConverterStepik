package kz.btokmyrza.currencyconverterv2.presentation.fragments.convertor.dialogs

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kz.btokmyrza.currencyconverterv2.R
import kz.btokmyrza.currencyconverterv2.presentation.adapters.ConvertCurrencyAdapter
import kz.btokmyrza.currencyconverterv2.presentation.models.Currency

class DeleteDialogFragment(
    private val convertCurrencyAdapter: ConvertCurrencyAdapter,
    private val currencyToDelete: Currency
) : DialogFragment(R.layout.dialog_delete) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            findViewById<Button>(R.id.btnCancelDeletion).setOnClickListener {
                dismiss()
            }
            findViewById<Button>(R.id.btnConfirmDeletion).setOnClickListener {
                val removedItemIndex = convertCurrencyAdapter.deleteItem(currencyToDelete)
                dismiss()

                val viewMainActivity = requireActivity().findViewById<View>(android.R.id.content)
                val bottomBar = viewMainActivity.findViewById<BottomNavigationView>(R.id.nav_view)

                val undoDeleteSnackbar = Snackbar.make(
                    viewMainActivity.findViewById(R.id.container),
                    "Валюта удалена",
                    Snackbar.LENGTH_SHORT
                )
                undoDeleteSnackbar.anchorView = bottomBar
                undoDeleteSnackbar.setAction("ВЕРНУТЬ") {
                    convertCurrencyAdapter.addItemAt(currencyToDelete, removedItemIndex)
                }
                undoDeleteSnackbar.show()
            }
        }
    }

}