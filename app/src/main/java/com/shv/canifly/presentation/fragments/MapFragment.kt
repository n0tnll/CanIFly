package com.shv.canifly.presentation.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.style.utils.ColorUtils.colorToRgbaString
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.createPolygonAnnotationManager
import com.shv.canifly.databinding.FragmentMapBinding
import com.shv.canifly.domain.util.FlyfrBasePainter
import dji.sdk.keyvalue.value.common.LocationCoordinate2D
import dji.v5.common.callback.CommonCallbacks
import dji.v5.common.error.IDJIError
import dji.v5.manager.aircraft.flysafe.FlyZoneManager
import dji.v5.manager.aircraft.flysafe.info.FlyZoneCategory
import dji.v5.manager.aircraft.flysafe.info.FlyZoneCategory.AUTHORIZATION
import dji.v5.manager.aircraft.flysafe.info.FlyZoneCategory.ENHANCED_WARNING
import dji.v5.manager.aircraft.flysafe.info.FlyZoneCategory.RESTRICTED
import dji.v5.manager.aircraft.flysafe.info.FlyZoneCategory.WARNING
import dji.v5.manager.aircraft.flysafe.info.FlyZoneInformation
import dji.v5.manager.aircraft.flysafe.info.MultiPolygonFlyZoneShape
import kotlinx.coroutines.launch

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding: FragmentMapBinding
        get() = _binding ?: throw RuntimeException("FragmentMapBinding is null")

    private val annotationApi by lazy {
        binding.mapView.annotations
    }
    private val polygonAnnotationManager by lazy {
        annotationApi.createPolygonAnnotationManager()
    }
    private val circleAnnotationManager by lazy {
        annotationApi.createCircleAnnotationManager()
    }

    private val painter: FlyfrBasePainter = FlyfrBasePainter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNFZ()
    }

    private fun getNFZ() {
        val flyZoneManager = FlyZoneManager.getInstance()
        val location = LocationCoordinate2D(43.1056, 131.8735)
        flyZoneManager.getFlyZonesInSurroundingArea(
            location,
            object : CommonCallbacks.CompletionCallbackWithParam<List<FlyZoneInformation>> {
                override fun onSuccess(flyZones: List<FlyZoneInformation>?) {
                    Toast.makeText(
                        requireContext(),
                        "Get surrounding Fly Zones Success!",
                        Toast.LENGTH_LONG
                    ).show()
                    updateFlyZonesOnTheMap(flyZones)
                    showSurroundFlyZonesInfo(flyZones)
                }

                override fun onFailure(error: IDJIError) {
                    Toast.makeText(
                        requireContext(),
                        error.description(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun updateFlyZonesOnTheMap(flyZones: List<FlyZoneInformation>?) {
        lifecycleScope.launch {
            flyZones?.let { zones ->
                for (flyZone in zones) {
                    val multiPolygonItems = flyZone.multiPolygonFlyZoneInformation
                    val itemSize = multiPolygonItems.size
                    for (i in 0 until itemSize) {
                        when (multiPolygonItems[i].shape) {
                            MultiPolygonFlyZoneShape.POLYGON -> {
                                Log.d(
                                    "updateFlyZonesOnTheMap",
                                    "sub polygon points " + i + " size: " + multiPolygonItems[i].polygonPoints.size
                                )
                                Log.d(
                                    "updateFlyZonesOnTheMap",
                                    "sub polygon points " + i + " category: " + flyZone.category.value()
                                )
                                Log.d(
                                    "updateFlyZonesOnTheMap",
                                    "sub polygon points " + i + " limit height: " + multiPolygonItems[i].limitedHeight
                                )
                                addPolygonMarker(
                                    multiPolygonItems[i].polygonPoints,
                                    flyZone.category,
                                    multiPolygonItems[i].limitedHeight
                                )
                            }

                            MultiPolygonFlyZoneShape.CYLINDER -> {
                                val tmpPos = multiPolygonItems[i].cylinderCenter
                                val subRadius = multiPolygonItems[i].cylinderRadius

                                Log.d(
                                    "updateFlyZonesOnTheMap",
                                    "sub circle points " + i + " coordinate: " + tmpPos.latitude + "," + tmpPos.longitude
                                )
                                Log.d(
                                    "updateFlyZonesOnTheMap",
                                    "sub circle points $i radius: $subRadius"
                                )

                                val circleAnnotationOptions = CircleAnnotationOptions()
                                    .withPoint(Point.fromLngLat(tmpPos.longitude, tmpPos.latitude))
                                    .withCircleRadius(subRadius)
                                    .withCircleOpacity(.5)

                                when (flyZone.category) {
                                    WARNING -> circleAnnotationOptions.withCircleColor(
                                        Color.parseColor(
                                            "#00FF007D"
                                        )
                                    )

                                    AUTHORIZATION -> circleAnnotationOptions.withCircleColor(
                                        Color.parseColor(
                                            "#ff00007D"
                                        )
                                    )

                                    RESTRICTED -> circleAnnotationOptions.withCircleColor(
                                        Color.parseColor(
                                            "#ff00007D"
                                        )
                                    )

                                    ENHANCED_WARNING -> circleAnnotationOptions.withCircleColor(
                                        Color.parseColor("#0000FF7D")
                                    )

                                    else -> {}
                                }

                                circleAnnotationManager.create(circleAnnotationOptions)
                            }

                            else -> {
                                val circleAnnotationOptions = CircleAnnotationOptions()
                                    .withPoint(
                                        Point.fromLngLat(
                                            flyZone.circleCenter.longitude,
                                            flyZone.circleCenter.latitude
                                        )
                                    )
                                    .withCircleRadius(flyZone.circleRadius)
                                    .withCircleOpacity(.5)
                                when (flyZone.category) {
                                    WARNING -> circleAnnotationOptions.withCircleColor(
                                        colorToRgbaString(Color.GREEN)
                                    )

                                    ENHANCED_WARNING -> circleAnnotationOptions.withCircleStrokeColor(
                                        colorToRgbaString(Color.BLUE)
                                    )

                                    AUTHORIZATION -> {
                                        circleAnnotationOptions.withCircleStrokeColor(
                                            colorToRgbaString(Color.YELLOW)
                                        )
                                    }

                                    RESTRICTED -> circleAnnotationOptions.withCircleStrokeColor(
                                        colorToRgbaString(Color.RED)
                                    )

                                    else -> {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showSurroundFlyZonesInfo(
        flyZones: List<FlyZoneInformation>?
    ) {
        val sb = StringBuffer()
        flyZones?.let { zones ->
            for (zone in zones) {
                val mp = zone.multiPolygonFlyZoneInformation
                sb.append("Category: ").append(zone.category.name).append("\n")
                sb.append("Latitude: ").append(zone.circleCenter.latitude).append("\n")
                sb.append("Longitude: ").append(zone.circleCenter.longitude).append("\n")
                sb.append("FlyZoneType: ").append(zone.flyZoneType.name).append("\n")
                sb.append("Radius: ").append(zone.circleRadius).append("\n")
                sb.append("Max height: ").append().append("\n")
                sb.append("Name: ").append(zone.name).append("\n")
            }
        }
        binding.tvFlyZoneInfo.text = sb.toString()
    }

    private fun addPolygonMarker(
        polygonPoints: List<LocationCoordinate2D>?,
        flyZoneCategory: FlyZoneCategory,
        height: Int
    ) {
        if (polygonPoints == null)
            return

        val shape = mutableListOf<MutableList<Point>>()
        val points = mutableListOf<Point>()
        for (point in polygonPoints) {
            points.add(Point.fromLngLat(point.longitude, point.latitude))
        }
        shape.add(points)

        var fillColor = "#78FF00"
        var fillOpacity = .5

        if (painter.heightToColor[height] != null) {
            painter.heightToColor[height]?.let {
                fillOpacity = it.toDouble()
            }
        } else if (flyZoneCategory == AUTHORIZATION) {
            fillColor = "#28FFFF"
        } else if (flyZoneCategory == ENHANCED_WARNING || flyZoneCategory == WARNING) {
            fillColor = "#0d1eff"
        }


        val polygonAnnotationOptions = PolygonAnnotationOptions()
            .withPoints(shape)
            .withFillColor(fillColor)
            .withFillOpacity(fillOpacity)

        polygonAnnotationManager.create(polygonAnnotationOptions)
    }


    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resId: Int): Bitmap? {
        val constantState =
            AppCompatResources.getDrawable(context, resId)?.constantState ?: return null
        val drawable = constantState.newDrawable().mutate()
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width / 4, canvas.height / 4)
        drawable.draw(canvas)
        return bitmap
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}