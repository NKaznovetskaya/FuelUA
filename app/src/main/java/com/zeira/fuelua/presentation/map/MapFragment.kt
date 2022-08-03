package com.zeira.fuelua.presentation.map

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.zeira.fuelua.R
import com.zeira.fuelua.core.presentation.BaseFragment
import com.zeira.fuelua.databinding.FragmentMapBinding
import com.zeira.fuelua.domain.models.GasStationModelX
import com.zeira.fuelua.presentation.details.DetailsBottomDialog
import com.zeira.fuelua.utils.Constants
import com.zeira.fuelua.utils.getAddress
import org.koin.androidx.viewmodel.ext.android.viewModel

//private val auth = FirebaseAuth.getInstance()
//var currentUser = auth.currentUser

class MapFragment : BaseFragment<FragmentMapBinding>(), GoogleMap.OnMarkerClickListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMapBinding
        get() = FragmentMapBinding::inflate

    private lateinit var mMap: GoogleMap

    private val center = LatLng(49.03848709571399, 31.451302026894254)

    private var gasStationList: ArrayList<GasStationModelX> = arrayListOf()
    private val vm: MapViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showProgressDialog(context)
        vm.getAllGasStation()


        vm.gasStationListLive.observe(viewLifecycleOwner) {
            gasStationList = it
            setupMap(gasStationList)
        }

        vm.exceptionLive.observe(viewLifecycleOwner) {
            if (it) {
                hideProgressDialog()
            }
        }


    }

    private fun setupMap(gasStationList: ArrayList<GasStationModelX>) {
        val supportMapFragment: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment

        supportMapFragment.getMapAsync { googleMap ->

            try {
                val success = googleMap.setMapStyle(
                    context?.let {
                        MapStyleOptions.loadRawResourceStyle(
                            it,
                            R.raw.map_style
                        )
                    })
                if (!success) {
                    Log.e("MapStyle", "Style parsing failed.")
                }
            } catch (e: Resources.NotFoundException) {
                Log.e("MapStyle", "Can't find style. Error: ", e);
            }

            mMap = googleMap

            for (i in gasStationList.indices) {
                Log.i("station", gasStationList[i].toString())
                val stationModel = gasStationList[i]

                val latitude = stationModel.latitude
                val longitude = stationModel.longitude
                if (latitude != null && longitude != null) {

                    val address =
                        requireContext().getAddress(lat = latitude, lng = longitude)
                    Log.i("address", address.toString())

                    val iconURL = stationModel.logo.toString()

                    val gasStation = LatLng(latitude, longitude)
                    val marker = mMap.addMarker(
                        MarkerOptions().apply {
                            position(gasStation)
                            title(address)
                        }
                        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_wog_logo))
                    )
                    marker?.tag = stationModel.id.toString()
                }
            }

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 5f))
            hideProgressDialog()

            mMap.setOnMarkerClickListener(this@MapFragment)

        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        showProgressDialog(context)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 15f))

        Log.i("markerClick", marker.tag.toString())

        val bottomSheet = DetailsBottomDialog()
        val testArgs = Bundle()

        var model: GasStationModelX? = null
        for (i in gasStationList.indices) {
            val checkModel = gasStationList[i]
            if (marker.tag == checkModel.id.toString()) {
                model = checkModel
            }
        }

        testArgs.putParcelable(Constants.STATION_DETAILS, model)
        bottomSheet.arguments = testArgs

        childFragmentManager.let {
            bottomSheet.show(
                it,
                DetailsBottomDialog.DETAILS_BOTTOM_DIALOG_TAG
            )
        }
        hideProgressDialog()

        return false
    }


}