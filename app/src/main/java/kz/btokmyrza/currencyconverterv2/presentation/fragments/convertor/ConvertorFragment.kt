package kz.btokmyrza.currencyconverterv2.presentation.fragments.convertor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.*
import dagger.hilt.android.AndroidEntryPoint
import kz.btokmyrza.currencyconverterv2.R
import kz.btokmyrza.currencyconverterv2.databinding.FragmentConverterBinding
import kz.btokmyrza.currencyconverterv2.presentation.adapters.ConvertCurrencyAdapter
import kz.btokmyrza.currencyconverterv2.presentation.fragments.convertor.dialogs.AddCurrencyBottomSheetDialog
import kz.btokmyrza.currencyconverterv2.presentation.fragments.convertor.dialogs.DeleteDialogFragment
import javax.inject.Inject

@AndroidEntryPoint
class ConvertorFragment : Fragment() {

    private var _binding: FragmentConverterBinding? = null
    private val binding get() = _binding!!

    private val convertorViewModel: ConvertorViewModel by viewModels()

    @Inject
    lateinit var convertCurrencyAdapter: ConvertCurrencyAdapter

    private var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConverterBinding.inflate(inflater, container, false)

        val mActionBarToolbar = activity?.findViewById<Toolbar>(R.id.action_bar)
        mActionBarToolbar?.title = "Convertor"

        linearLayoutManager = LinearLayoutManager(activity)
        val smoothScroller = object : LinearSmoothScroller(activity) {
            override fun getVerticalSnapPreference(): Int = SNAP_TO_START
        }
        setupCurrencyAdapter()

        convertorViewModel.convertorCurrencies.observeForever { currencyList ->
            convertCurrencyAdapter.currencies = currencyList
        }

        binding.tietCurrencyAmount.addTextChangedListener { enteredTengeAmount ->
            if ((enteredTengeAmount != null) && enteredTengeAmount.toString().isNotBlank()) {
                convertCurrencyAdapter.convertCurrencies(enteredTengeAmount)
            }
        }

        binding.fabAddToDatabase.setOnClickListener {
            convertorViewModel.saveTransactionToDatabase(binding.tietCurrencyAmount.text.toString())
        }

        binding.fabAddCurrency.setOnClickListener {
            AddCurrencyBottomSheetDialog().show(childFragmentManager, null)
            smoothScroller.targetPosition = convertCurrencyAdapter.itemCount
            linearLayoutManager?.startSmoothScroll(smoothScroller)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupCurrencyAdapter() {
        val currencyRecyclerView = binding.rvCurrencyList
        convertCurrencyAdapter.setOnItemClickListener { clickedCurrencyItem ->
            val topActionBar = requireActivity().findViewById<Toolbar>(R.id.action_bar)
            val defaultTitle = topActionBar.title
            val defaultColor = topActionBar.background

            topActionBar.title = "Item selected"
            topActionBar.background =
                ContextCompat.getDrawable(topActionBar.context, R.color.toolbar_delete)
            topActionBar.menu.clear()

            val deleteButton = requireActivity().findViewById<ImageButton>(R.id.btnDelete)
            deleteButton.visibility = View.VISIBLE

            deleteButton.setOnClickListener {
                DeleteDialogFragment(convertCurrencyAdapter, clickedCurrencyItem).show(
                    childFragmentManager,
                    null
                )
                topActionBar.inflateMenu(R.menu.menu_main)
                it.visibility = View.GONE
                topActionBar.title = defaultTitle
                topActionBar.background = defaultColor
            }
        }

        val itemTouchHelper by lazy {
            ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                ) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        val from = viewHolder.adapterPosition
                        val to = target.adapterPosition

                        convertCurrencyAdapter.moveItem(from, to)

                        return true
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        convertCurrencyAdapter.deleteItemAt(viewHolder.adapterPosition)
                    }
                })
        }
        itemTouchHelper.attachToRecyclerView(currencyRecyclerView)

        currencyRecyclerView.apply {
            adapter = convertCurrencyAdapter
            layoutManager = linearLayoutManager
            itemAnimator = DefaultItemAnimator()
        }
    }

    fun sortCurrencyRecyclerViewItems(chosenSortingIndex: Int?) {
        convertCurrencyAdapter.sortItems(chosenSortingIndex)
    }
}