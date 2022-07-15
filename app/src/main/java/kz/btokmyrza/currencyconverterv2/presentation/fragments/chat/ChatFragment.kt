package kz.btokmyrza.currencyconverterv2.presentation.fragments.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ServerValue
import dagger.hilt.android.AndroidEntryPoint
import kz.btokmyrza.currencyconverterv2.R
import kz.btokmyrza.currencyconverterv2.databinding.FragmentChatBinding
import kz.btokmyrza.currencyconverterv2.presentation.adapters.ChatAdapter
import kz.btokmyrza.currencyconverterv2.presentation.models.ChatSender
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val chatViewModel: ChatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val mActionBarToolbar = activity?.findViewById<Toolbar>(R.id.action_bar)
        mActionBarToolbar?.title = "Chat"

        val chatAdapter = ChatAdapter()
        binding.rvChat.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)
            itemAnimator = DefaultItemAnimator()
        }

        chatViewModel.chatMessages.observeForever { chatMessages ->
            chatAdapter.setItems(chatMessages)
        }

        binding.ibSendMessage.setOnClickListener {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss.SSS")
            val formatted = current.format(formatter)

            val timestampMap = mapOf(
                "timestamp" to ServerValue.TIMESTAMP
            )

            val enteredMessage = binding.etMessage.text.toString()
            val chatMessage = ChatSender(
                dateSent = timestampMap,
                author = "Azat",
                message = enteredMessage
            )

            if (enteredMessage.isNotBlank()) {
                chatViewModel.prependChatItemFirebase(chatMessage)
                binding.etMessage.text.clear()
            }
        }

        chatViewModel.readListener()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}