package kz.btokmyrza.currencyconverterv2.presentation.fragments.personal_page.subsections

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kz.btokmyrza.currencyconverterv2.R
import kz.btokmyrza.currencyconverterv2.databinding.FragmentMainsBinding

class MainsFragment : Fragment() {

    private var _binding: FragmentMainsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val fullName = "Batyrkhan Tokmyrza"
        val email = "btokmyrza@gmail.com"

        binding.buttonShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
                val shareMessage = "Name: $fullName\nEmail: $email"
                putExtra(Intent.EXTRA_TEXT, shareMessage)
            }
            startActivity(Intent.createChooser(shareIntent, "Select app to share with"))
        }

        binding.buttonSendByEmail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "message/rfc822"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("recipient@example.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Stepik Домашнее Задание 12.5")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Разобрался как работают Неявные интенты")

            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."))
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(activity, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonCall.setOnClickListener {
            val phone = "+77052999982"
            val callIntent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            startActivity(callIntent)
        }

        binding.buttonStartCamera.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(cameraIntent)
        }


        return root
        // return inflater.inflate(R.layout.fragment_mains, container, false)
    }

}