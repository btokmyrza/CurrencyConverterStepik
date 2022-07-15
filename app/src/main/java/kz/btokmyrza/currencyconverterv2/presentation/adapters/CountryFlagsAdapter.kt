package kz.btokmyrza.currencyconverterv2.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kz.btokmyrza.currencyconverterv2.R
import kz.btokmyrza.currencyconverterv2.presentation.models.FlagCurrency

class CountryFlagsAdapter(context: Context) :
    ListAdapter<FlagCurrency, CountryFlagsAdapter.FlagCurrencyViewHolder>(FlagCurrencyDiffCallback()) {

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

    inner class FlagCurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class FlagCurrencyDiffCallback : DiffUtil.ItemCallback<FlagCurrency>() {
        override fun areItemsTheSame(oldItem: FlagCurrency, newItem: FlagCurrency): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: FlagCurrency, newItem: FlagCurrency): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlagCurrencyViewHolder {
        return FlagCurrencyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_item_flag,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FlagCurrencyViewHolder, position: Int) {
        val flagCurrency = this.currentList[position]
        holder.itemView.apply {
            val imageFlag = findViewById<ImageView>(R.id.ivFlag)
            val currencyName = findViewById<TextView>(R.id.tvCurrencyName)

            glide
                .load(flagCurrency.flagImageUrl)
                .into(imageFlag)
            currencyName.text = flagCurrency.name

            setOnClickListener {
                onFlagCurrencyItemClickListener?.let { it(flagCurrency) }
            }
        }
    }

    private var onFlagCurrencyItemClickListener: ((FlagCurrency) -> Unit)? = null

    fun setOnFlagCurrencyItemClickListener(listener: (FlagCurrency) -> Unit) {
        onFlagCurrencyItemClickListener = listener
    }

}