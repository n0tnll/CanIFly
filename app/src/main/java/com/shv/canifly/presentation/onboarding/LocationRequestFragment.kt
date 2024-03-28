package com.shv.canifly.presentation.onboarding

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.shv.canifly.R
import com.shv.canifly.data.extensions.openAppSettings
import com.shv.canifly.databinding.FragmentLocationPermissionRequestBinding
import com.shv.canifly.presentation.dialogs.LocationPermissionTextProvider
import com.shv.canifly.presentation.dialogs.permissionDialog
import com.shv.canifly.presentation.onboarding.NotificationRequestFragment.Companion.NOTIFICATION_KEY
import com.shv.canifly.presentation.onboarding.OnBoardingFragment.Companion.ONBOARDING_KEY

class LocationRequestFragment : Fragment() {

    private var _binding: FragmentLocationPermissionRequestBinding? = null
    private val binding: FragmentLocationPermissionRequestBinding
        get() = _binding ?: throw RuntimeException("FragmentPermissionRequestBinding is null")

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private val dialogQueue = mutableListOf<String>()

    private val permissionForRequest = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationPermissionRequestBinding.inflate(
            inflater,
            container,
            false
        )

        val btnNext = binding.btnNext
        val viewPager = activity?.findViewById<ViewPager2>(R.id.vp_start_screens)

        btnNext.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                viewPager?.currentItem = NEXT_SCREEN
            } else {
                setNotificationPreferences()
                viewPager?.currentItem = DRONE_SCREEN
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerPermissionListener()

        if (!checkLocationPermission()) {
            binding.btnNext.isEnabled = false
            binding.btnRequestPermission.setOnClickListener {
                permissionLauncher.launch(permissionForRequest)
            }
        } else {
            updateUI()
        }
    }

    private fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted && !dialogQueue.contains(permission)) {
            dialogQueue.add(permission)
        }
    }

    private fun setLocationPreferences() {
        val sharedPreferences =
            requireActivity().getSharedPreferences(ONBOARDING_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putBoolean(LOCATION_KEY, true)
        }.apply()
    }

    private fun setNotificationPreferences() {
        val sharedPreferences =
            requireActivity().getSharedPreferences(ONBOARDING_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putBoolean(NOTIFICATION_KEY, true)
        }.apply()
    }

    private fun registerPermissionListener() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { perms ->
            permissionForRequest.forEach { permission ->
                onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                )
            }

            val granted = perms.any { it.value }

            if (granted) {
                setLocationPreferences()
                updateUI()
            } else {
                showDialog(perms.keys.first())
            }
        }
    }

    private fun updateUI() {
        binding.btnNext.isEnabled = true
        binding.tvWhereRU.text = "Permission granted!"
        binding.tvWhereRUHint.visibility = View.GONE
        binding.btnRequestPermission.visibility = View.GONE
    }

    private fun showDialog(permission: String) {
        permissionDialog(
            context = requireContext(),
            permissionTextProvider = when (permission) {
                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    LocationPermissionTextProvider()
                }

                Manifest.permission.ACCESS_COARSE_LOCATION -> {
                    LocationPermissionTextProvider()
                }

                else -> {
                    return
                }
            },
            isPermanentlyDeclined = !shouldShowRequestPermissionRationale(permission),
            onOkClick = {
                dialogQueue.clear()
                permissionLauncher.launch(permissionForRequest)
            },
            onGoToAppSettingsClick = requireActivity()::openAppSettings
        )
    }

    private fun checkLocationPermission(): Boolean {
        val hasAccessLocationPermissions = arrayListOf(
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ).any {
            it == PackageManager.PERMISSION_GRANTED
        }

        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return hasAccessLocationPermissions && isGpsEnabled
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(): LocationRequestFragment {
            return LocationRequestFragment()
        }

        private const val NEXT_SCREEN = 2
        private const val DRONE_SCREEN = 3
        const val LOCATION_KEY = "location_set"
    }
}