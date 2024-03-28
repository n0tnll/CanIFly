package com.shv.canifly.presentation.screens.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.shv.canifly.R
import com.shv.canifly.databinding.AddZoneDialogBinding
import com.shv.canifly.databinding.FragmentMapBinding
import com.shv.canifly.domain.entity.Airport
import com.shv.canifly.domain.entity.AirportType.BALLOON_PORT
import com.shv.canifly.domain.entity.AirportType.HELIPORT
import com.shv.canifly.domain.entity.AirportType.LARGE_AIRPORT
import com.shv.canifly.domain.entity.AirportType.MEDIUM_AIRPORT
import com.shv.canifly.domain.entity.AirportType.SEAPLANE_BASE
import com.shv.canifly.domain.entity.AirportType.SMALL_AIRPORT
import com.shv.canifly.domain.entity.BadSignalZone
import com.shv.canifly.domain.util.ResourcesProvider
import com.shv.canifly.presentation.dialogs.BadSignalInfoBottomSheet
import com.shv.canifly.presentation.dialogs.NfzInfoBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback, OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private var _binding: FragmentMapBinding? = null
    private val binding: FragmentMapBinding
        get() = _binding ?: throw RuntimeException("FragmentMapBinding is null")

    private val viewModel: MapViewModel by viewModels()

    @Inject
    lateinit var resourcesProvider: ResourcesProvider

    private var currentLocation: Location? = null
    private var surroundZones = mutableListOf<Airport>()
    private var badSignalZones = mutableListOf<BadSignalZone>()

    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(
            inflater,
            container,
            false
        )
        lifecycleScope.launch {
            currentLocation = viewModel.getCurrentLocation()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        observeViewModel()
        viewModel.getNfz()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
        enableMyLocation()
        googleMap.setMinZoomPreference(10f)
        currentLocation?.let {
            val loc = LatLng(it.latitude, it.longitude)
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 10f))
        }
        map.setOnCircleClickListener {
            val clickedZone = surroundZones.find { airport ->
                it.center.latitude == airport.latitude && it.center.longitude == airport.longitude
            }
            clickedZone?.let {
                val nfzInfoBottomSheet = NfzInfoBottomSheet.newInstance().apply {
                    arguments = Bundle().apply {
                        putParcelable(NfzInfoBottomSheet.NFZ_ARG, it)
                    }
                }

                nfzInfoBottomSheet.show(
                    requireActivity().supportFragmentManager,
                    NfzInfoBottomSheet.TAG
                )
            }
            val clickedBadZone = badSignalZones.find { badSignalZone ->
                it.center == badSignalZone.latLng
            }
            clickedBadZone?.let {
                val badSignalInfoBottomSheet = BadSignalInfoBottomSheet.newInstance().apply {
                    arguments = Bundle().apply {
                        putParcelable(BadSignalInfoBottomSheet.BAD_SIGNAL_ARG, clickedBadZone)
                    }
                }

                badSignalInfoBottomSheet.show(
                    requireActivity().supportFragmentManager,
                    BadSignalInfoBottomSheet.TAG
                )
            }
        }
        map.setOnMapLongClickListener { latLng ->
            showAddBadSignalZoneDialog(latLng)
        }
    }

    private fun showAddBadSignalZoneDialog(latLng: LatLng) {
        val zoneFormView = AddZoneDialogBinding.inflate(LayoutInflater.from(requireContext()))
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resourcesProvider.getString(R.string.add_bad_signal_zone_dialog_title))
            .setView(zoneFormView.root)
            .setNegativeButton(resourcesProvider.getString(R.string.dialog_cancel), null)
            .setPositiveButton(resourcesProvider.getString(R.string.dialog_add)) { dialog, _ ->
                val description = zoneFormView.etDescription.text.toString()
                val radius = zoneFormView.etRadius.text.toString().toDouble()
                badSignalZones.add(
                    BadSignalZone(
                        description = description,
                        radius = radius.toInt(),
                        latLng = latLng
                    )
                )
                val circleOption = CircleOptions()
                    .center(latLng)
                    .radius(radius)
                    .strokeWidth(5f)
                    .strokeColor(resourcesProvider.getColor(R.color.bad_signal))
                    .fillColor(resourcesProvider.getColorWithAlpha(R.color.bad_signal))
                    .clickable(true)

                map.addCircle(circleOption)
                dialog.dismiss()
            }
            .show()
    }

//    Mavic 3 classic, without FCC & 5.8 GHz

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(requireContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(requireContext(), "Current location:\n$location", Toast.LENGTH_LONG).show()
    }

    private fun enableMyLocation() {
        if (!::map.isInitialized) return
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

        if (hasAccessLocationPermissions) {
            map.isMyLocationEnabled = true
        } else return
    }


    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect {
                    it.nfzList?.let { nfzList ->
                        showNfz(nfzList)
                    }
                    it.error?.let { errorMsg ->
                        Toast.makeText(
                            requireContext(),
                            "Error: $errorMsg",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }


    private fun showNfz(nfzList: List<Airport>) {
        surroundZones.addAll(getSurroundZones(nfzList))
        for (nfz in surroundZones) {
            addCircleNfz(nfz)
        }
    }

    private fun getSurroundZones(nfzList: List<Airport>): List<Airport> {
        val loc = currentLocation ?: throw RuntimeException("current location is null")
        return nfzList.filter {
            val distance = distanceBetween(
                loc.latitude,
                loc.longitude,
                it.latitude,
                it.longitude
            )
            distance <= RADIUS
        }
    }

    private fun addCircleNfz(airport: Airport) {
        var fillColor = 0
        var strokeColor = 0

        when (airport.type) {
            LARGE_AIRPORT -> {
                strokeColor = resourcesProvider.getColor(R.color.large_airport)
                fillColor = resourcesProvider.getColorWithAlpha(R.color.large_airport)
            }

            MEDIUM_AIRPORT -> {
                strokeColor = resourcesProvider.getColor(R.color.medium_airport)
                fillColor = resourcesProvider.getColorWithAlpha(R.color.medium_airport)
            }

            SMALL_AIRPORT -> {
                strokeColor = resourcesProvider.getColor(R.color.small_airport)
                fillColor = resourcesProvider.getColorWithAlpha(R.color.small_airport)
            }

            HELIPORT -> {
                strokeColor = resourcesProvider.getColor(R.color.heliport)
                fillColor = resourcesProvider.getColorWithAlpha(R.color.heliport)
            }

            SEAPLANE_BASE -> {
                strokeColor = resourcesProvider.getColor(R.color.seaplane_base)
                fillColor = resourcesProvider.getColorWithAlpha(R.color.seaplane_base)
            }

            BALLOON_PORT -> {
                strokeColor = resourcesProvider.getColor(R.color.balloon_port)
                fillColor = resourcesProvider.getColorWithAlpha(R.color.balloon_port)
            }

            null -> {
                strokeColor = resourcesProvider.getColor(R.color.large_airport)
                fillColor = resourcesProvider.getColorWithAlpha(R.color.large_airport)
            }
        }

        val circleOption = CircleOptions()
            .center(LatLng(airport.latitude, airport.longitude))
            .radius(airport.type?.nfzRadius ?: 1000.0)
            .strokeWidth(5f)
            .strokeColor(strokeColor)
            .fillColor(fillColor)
            .clickable(true)

        map.addCircle(circleOption)
    }

    private fun distanceBetween(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {
        val earthRadius = 6371000
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a =
            sin(dLat / 2) * sin(dLat / 2) + cos(Math.toRadians(lat1)) * cos(
                Math.toRadians(
                    lat2
                )
            ) * sin(
                dLon / 2
            ) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadius * c
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val RADIUS = 50_000
    }
}