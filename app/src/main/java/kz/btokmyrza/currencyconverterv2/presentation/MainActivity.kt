package kz.btokmyrza.currencyconverterv2.presentation

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kz.btokmyrza.currencyconverterv2.R
import kz.btokmyrza.currencyconverterv2.data.remote.work_managers.DownloadWorker
import kz.btokmyrza.currencyconverterv2.databinding.ActivityMainBinding
import kz.btokmyrza.currencyconverterv2.presentation.fragments.convertor.ConvertorFragment

const val TAG = "MainActivityToken"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var chosenIndex: Int? = null
    private lateinit var navHostFragment: NavHostFragment

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.actionBar)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navigationController = navHostFragment.navController
        binding.navView.setupWithNavController(navigationController)

        mainViewModel.chosenIndex.observe(this) {
            chosenIndex = it
        }

        getFirebaseRegistrationToken()

        val downloadRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.UNMETERED)
                    .setRequiresBatteryNotLow(true)
                    .build()
            ).build()
        WorkManager.getInstance(applicationContext).beginUniqueWork(
            "download",
            ExistingWorkPolicy.KEEP,
            downloadRequest
        ).enqueue()
    }

    private fun getFirebaseRegistrationToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.d(TAG, token)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return when (navHostFragment.childFragmentManager.fragments[0]) {
            is ConvertorFragment -> {
                val inflater: MenuInflater = menuInflater
                inflater.inflate(R.menu.menu_main, menu)
                super.onCreateOptionsMenu(menu)
            }
            else -> {
                super.onCreateOptionsMenu(menu)
            }
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val chosenSortingOptionId = when (chosenIndex) {
            0 -> R.id.submenu_sortByAlphabet
            1 -> R.id.submenu_sortByAmount
            else -> return super.onPrepareOptionsMenu(menu)
        }

        menu.findItem(chosenSortingOptionId)?.isChecked = true

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.submenu_sortByAlphabet -> {
                chosenIndex = 0
                true
            }
            R.id.submenu_sortByAmount -> {
                chosenIndex = 1
                true
            }
            R.id.menu_resetSort -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}