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
import kz.btokmyrza.currencyconverterv2.domain.enitities.Transaction

class TransactionHistoryAdapter :
    RecyclerView.Adapter<TransactionHistoryAdapter.TransactionHistoryViewHolder>() {

    inner class TransactionHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(
            oldItem: Transaction,
            newItem: Transaction
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Transaction,
            newItem: Transaction
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var transactions: List<Transaction>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionHistoryViewHolder {
        return TransactionHistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_item_transaction,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TransactionHistoryViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.itemView.apply {
            val tvTransactionAmount = this.findViewById<TextView>(R.id.tvTransactionAmount)
            tvTransactionAmount.text = transaction.amount.toString()

            val ibDelete = this.findViewById<ImageView>(R.id.ibDelete)
            ibDelete.setOnClickListener {
                onTransactionClickListener?.let { it(transaction) }
            }
        }
    }

    private var onTransactionClickListener: ((Transaction) -> Unit)? = null

    fun setOnTransactionClickListener(listener: (Transaction) -> Unit) {
        onTransactionClickListener = listener
    }

    override fun getItemCount(): Int = transactions.size

    fun deleteItem(transaction: Transaction): Int {
        val currentList = transactions.toMutableList()
        val id = transaction.id
        val removedIndex = currentList.indexOfFirst { it.id == id }
        currentList.removeAt(removedIndex)
        transactions = currentList
        return removedIndex
    }

}