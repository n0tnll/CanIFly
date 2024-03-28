package com.shv.canifly.presentation.onboarding

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.shv.canifly.R
import com.shv.canifly.data.extensions.openAppSettings
import com.shv.canifly.databinding.FragmentNotificationPermissionRequestBinding
import com.shv.canifly.presentation.dialogs.NotificationPermissionTextProvider
import com.shv.canifly.presentation.dialogs.permissionDialog
import com.shv.canifly.presentation.onboarding.OnBoardingFragment.Companion.ONBOARDING_KEY

class NotificationRequestFragment : Fragment() {

    private var _binding: FragmentNotificationPermissionRequestBinding? = null
    private val binding: FragmentNotificationPermissionRequestBinding
        get() = _binding ?: throw RuntimeException("FragmentPermissionRequestBinding is null")

    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private val dialogQueue = mutableListOf<String>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val permissionForRequest = Manifest.permission.POST_NOTIFICATIONS

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationPermissionRequestBinding.inflate(
            inflater,
            container,
            false
        )

        val btnNext = binding.btnNext
        val viewPager = activity?.findViewById<ViewPager2>(R.id.vp_start_screens)

        btnNext.setOnClickListener {
            viewPager?.currentItem = NEXT_SCREEN
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerPermissionListener()
            if (!checkNotificationPermission()) {
                binding.btnNext.isEnabled = false
                binding.btnNotificationPermission.setOnClickListener {
                    requestLocationPermission()
                }
            }
        } else {
            updateUI()
        }
    }

    private fun setNotificationPreferences() {
        val sharedPreferences =
            requireActivity().getSharedPreferences(ONBOARDING_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putBoolean(NOTIFICATION_KEY, true)
        }.apply()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestLocationPermission() {
        if (!checkNotificationPermission()) {
            permissionLauncher.launch(permissionForRequest)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun registerPermissionListener() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            onPermissionResult(
                permission = permissionForRequest,
                isGranted = isGranted
            )
            if (isGranted) {
                updateUI()
                setNotificationPreferences()
            } else {
                showDialog()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showDialog() {
        dialogQueue.forEach { permission ->
            permissionDialog(
                context = requireContext(),
                permissionTextProvider = NotificationPermissionTextProvider(),
                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(permission),
                onOkClick = {
                    dialogQueue.clear()
                    permissionLauncher.launch(permissionForRequest)
                },
                onGoToAppSettingsClick = requireActivity()::openAppSettings
            )
        }
    }

    private fun updateUI() {
        binding.btnNext.isEnabled = true
        binding.tvGetNotified.text = "Permission granted!"
        binding.tvNotificationHint.visibility = View.GONE
        binding.btnNotificationPermission.visibility = View.GONE
    }

    private fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted && !dialogQueue.contains(permission)) {
            dialogQueue.add(permission)
        }
    }

    private fun checkNotificationPermission(): Boolean {
        val hasAccessNotification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
        return hasAccessNotification
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(): NotificationRequestFragment {
            return NotificationRequestFragment()
        }

        private const val NEXT_SCREEN = 3
        const val NOTIFICATION_KEY = "notification_set"
    }
}