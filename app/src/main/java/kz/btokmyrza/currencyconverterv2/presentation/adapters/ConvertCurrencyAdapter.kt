package kz.btokmyrza.currencyconverterv2.presentation.adapters

import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kz.btokmyrza.currencyconverterv2.R
import kz.btokmyrza.currencyconverterv2.presentation.models.Currency
import java.util.*

class ConvertCurrencyAdapter(context: Context) :
    RecyclerView.Adapter<ConvertCurrencyAdapter.CurrencyViewHolder>() {

    private var glide: RequestManager

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface GlideEntryPoint {
        fun getGlideInstance(): RequestManager
    }

    init {
        val glideEntryPoint =
            EntryPointAccessors.fromApplication(context, GlideEntryPoint::class.java)
        glide = glideEntryPoint.getGlideInstance()
    }

    inner class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var currencies: List<Currency>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_item_currency,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val convertCurrency = currencies[position]
        holder.itemView.apply {
            val editCurrency = this.findViewById<TextInputLayout>(R.id.tilCurrencyAmount)
            val currencyAmount = this.findViewById<TextInputEditText>(R.id.tietCurrencyAmount)
            val flag = this.findViewById<ImageView>(R.id.ivFlagTenge)
            val currencyName = this.findViewById<TextView>(R.id.tvCurrencyAndCountryName)

            editCurrency.hint = convertCurrency.name
            currencyAmount.setText(convertCurrency.amount.toString())
            glide
                .load(convertCurrency.flagImageUrl)
                .into(flag)
            currencyName.text = convertCurrency.name

            currencyName.setOnLongClickListener {
                onItemClickListener?.let { it(convertCurrency) }
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return currencies.size
    }

    private var onItemClickListener: ((Currency) -> Unit)? = null

    fun setOnItemClickListener(listener: (Currency) -> Unit) {
        onItemClickListener = listener
    }

    fun deleteItemAt(position: Int) {
        val currentList = currencies.toMutableList()
        currentList.removeAt(position)
        currencies = currentList
    }

    fun deleteItem(currency: Currency): Int {
        val currentList = currencies.toMutableList()

        val id = currency.id
        val removedIndex = currentList.indexOfFirst { it.id == id }
        currentList.removeAt(removedIndex)

        currencies = currentList

        return removedIndex
    }

    fun addItemAt(currency: Currency, position: Int) {
        val currentList = currencies.toMutableList()
        currentList.add(position, currency)
        currencies = currentList
    }

    fun addItem(currency: Currency) {
        val currentList = currencies.toMutableList()
        currentList.add(currencies.size, currency)
        currencies = currentList
    }

    fun moveItem(from: Int, to: Int) {
        val currentList = currencies.toMutableList()
        Collections.swap(currentList, from, to)
        currencies = currentList
        notifyItemMoved(from, to)
    }

    fun sortItems(chosenSortingIndex: Int?) {
        val currentList = currencies.toMutableList()
        when (chosenSortingIndex) {
            0 -> currentList.sortBy { it.name }
            1 -> currentList.sortBy { -it.amount }
            else -> Unit
        }
        currencies = currentList
    }

    fun convertCurrencies(enteredTengeAmount: Editable?) {
        Thread {
            val currentList = currencies.toMutableList()
            currentList.forEach { currency ->
                currency.amount = (currency.conversionRate * enteredTengeAmount.toString().toFloat()).toInt()
            }
            currencies = currentList

        }.start()
        notifyDataSetChanged()
    }

}
