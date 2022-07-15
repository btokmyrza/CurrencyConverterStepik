package kz.btokmyrza.currencyconverterv2.presentation.fragments.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kz.btokmyrza.currencyconverterv2.R
import kz.btokmyrza.currencyconverterv2.databinding.FragmentHistoryBinding
import kz.btokmyrza.currencyconverterv2.presentation.adapters.TransactionHistoryAdapter

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val historyViewModel: HistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val mActionBarToolbar = activity?.findViewById<Toolbar>(R.id.action_bar)
        mActionBarToolbar?.title = "History"

        val transactionHistoryAdapter = TransactionHistoryAdapter()
        binding.rvTransactionsHistory.apply {
            adapter = transactionHistoryAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }
        transactionHistoryAdapter.setOnTransactionClickListener { transaction ->
            val removedIndex = historyViewModel.deleteTransaction(transaction)

            val undoDeleteSnackbar = Snackbar.make(
                root,
                "Удалено",
                Snackbar.LENGTH_SHORT
            )
            undoDeleteSnackbar.anchorView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
            undoDeleteSnackbar.setAction("Восстановить") {
                historyViewModel.returnTransaction(transaction, removedIndex)
            }
            undoDeleteSnackbar.show()
        }

        historyViewModel.loadAllTransactions()
        historyViewModel.transactions.observe(viewLifecycleOwner) {
            transactionHistoryAdapter.transactions = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}