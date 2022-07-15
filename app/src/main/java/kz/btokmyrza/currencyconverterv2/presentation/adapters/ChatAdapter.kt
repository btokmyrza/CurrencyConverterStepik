package kz.btokmyrza.currencyconverterv2.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.btokmyrza.currencyconverterv2.R
import kz.btokmyrza.currencyconverterv2.presentation.models.*

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ChatSenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class ChatReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class ChatDateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val data = mutableListOf<ChatItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.rv_item_chat_sender -> ChatSenderViewHolder(
                inflater.inflate(
                    R.layout.rv_item_chat_sender,
                    parent,
                    false
                )
            )
            R.layout.rv_item_chat_receiver -> ChatReceiverViewHolder(
                inflater.inflate(
                    R.layout.rv_item_chat_receiver,
                    parent,
                    false
                )
            )
            else -> ChatDateViewHolder(
                inflater.inflate(
                    R.layout.rv_item_chat_date,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is ChatSender -> R.layout.rv_item_chat_sender
            is ChatReceiver -> R.layout.rv_item_chat_receiver
            else -> R.layout.rv_item_chat_date
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val chatItem = data[position]) {
            is ChatSender -> {
                holder.itemView.apply {
                    val tvAvatarSender = this.findViewById<TextView>(R.id.tvAvatarSender)
                    val tvMessageSender = this.findViewById<TextView>(R.id.tvMessageSender)
                    tvAvatarSender.text = chatItem.author?.first()?.uppercase() ?: ""
                    tvMessageSender.text = chatItem.message
                }
            }
            is ChatReceiver -> {
                holder.itemView.apply {
                    val tvAvatarReceiver = this.findViewById<TextView>(R.id.tvAvatarReceiver)
                    val tvMessageReceiver = this.findViewById<TextView>(R.id.tvMessageReceiver)
                    tvAvatarReceiver.text = chatItem.author?.first()?.uppercase() ?: ""
                    tvMessageReceiver.text = chatItem.message
                }
            }
            is ChatDate -> {
                holder.itemView.apply {
                    val tvChatDate = this.findViewById<TextView>(R.id.tvChatDate)
                    tvChatDate.text = chatItem.date
                }
            }
        }
    }

    override fun getItemCount(): Int = data.size

    fun setItems(list: List<ChatItem>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

}