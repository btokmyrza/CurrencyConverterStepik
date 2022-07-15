package kz.btokmyrza.currencyconverterv2.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kz.btokmyrza.currencyconverterv2.R
import kz.btokmyrza.currencyconverterv2.presentation.models.SearchCurrency

class SearchCurrencyAdapter : RecyclerView.Adapter<SearchCurrencyAdapter.SearchCurrencyViewHolder>() {

    inner class SearchCurrencyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<SearchCurrency>() {
        override fun areItemsTheSame(oldItem: SearchCurrency, newItem: SearchCurrency): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: SearchCurrency, newItem: SearchCurrency): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCurrencyViewHolder {
        return SearchCurrencyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_item_search,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchCurrencyViewHolder, position: Int) {
        val searchCurrency = differ.currentList[position]
        holder.itemView.apply {
            val imageFlag = this.findViewById<ImageView>(R.id.ivFlagTenge)
            val currencyName = this.findViewById<TextView>(R.id.tvCurrencyAndCountryName)

            imageFlag.setImageResource(searchCurrency.flagImage)
            currencyName.text = searchCurrency.name

            setOnClickListener {
                onItemClickListener?.let { it(searchCurrency) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((SearchCurrency) -> Unit)? = null

    fun setOnItemClickListener(listener: (SearchCurrency) -> Unit) {
        onItemClickListener = listener
    }

}