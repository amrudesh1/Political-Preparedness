package com.example.android.politicalpreparedness.representative

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.android.politicalpreparedness.*
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import com.google.android.gms.location.*
import java.lang.Exception
import java.util.*


class DetailFragment : Fragment() {

    lateinit var fragmentRepresentativeBinding: FragmentRepresentativeBinding
    lateinit var address1: EditText
    lateinit var address2: EditText
    lateinit var city: EditText
    lateinit var zip: EditText
    lateinit var state: Spinner


    private val representativeViewModel by viewModels<RepresentativeViewModel> { getViewModelFactory() }

    private lateinit var representativeListAdapter: RepresentativeListAdapter


    companion object {
        //TODO: Add Constant for Location request
        const val PERMISSION_REQUEST_LOCATION = 112
        lateinit var locationManager: LocationManager
        var firstTime = true
    }

    //TODO: Declare ViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentRepresentativeBinding = FragmentRepresentativeBinding.inflate(inflater, container, false)

        address1 = fragmentRepresentativeBinding.scrollableHeader.addressLine1
        address2 = fragmentRepresentativeBinding.scrollableHeader.addressLine2
        city = fragmentRepresentativeBinding.scrollableHeader.city
        zip = fragmentRepresentativeBinding.scrollableHeader.zip
        state = fragmentRepresentativeBinding.scrollableHeader.state


        val states = resources.getStringArray(R.array.states)
        val adapter = activity?.applicationContext?.let {
            ArrayAdapter(it,
                    android.R.layout.simple_spinner_item, states)
        }
        state.adapter = adapter



        fragmentRepresentativeBinding.scrollableHeader.buttonSearch.setOnClickListener {
            hideKeyboard()
            if (checkFieldsAreEmpty()) {
                val address = Address(address1.text.toString(), address2.text.toString(), city.text.toString(), state.selectedItem.toString(), zip.text.toString()).toFormattedString()
                getRepList(address)

            }
        }

        fragmentRepresentativeBinding.scrollableHeader.buttonLocation.setOnClickListener {
            if (checkLocationPermissions()) {
                getLocation()
            }
        }



        return fragmentRepresentativeBinding.root
    }

    private fun getRepList(address: String) {
        firstTime = false
        representativeViewModel.getRepresentatives(address).observe(viewLifecycleOwner, Observer {
            representativeListAdapter = RepresentativeListAdapter()
            fragmentRepresentativeBinding.representationViewModel = representativeViewModel
            fragmentRepresentativeBinding.representativeRecyclerView.adapter = representativeListAdapter

        })
    }

    private fun checkFieldsAreEmpty(): Boolean {
        var error = true
        if (address1.text.isEmpty()) {
            error = false
            showToast("Address is Empty")
        }

        if (city.text.isEmpty()) {
            error = false
            showToast("City is Empty")
        }

        if (zip.text.isEmpty()) {
            error = false
            showToast("Zip is Empty")
        }
        return error
    }


    fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("Permission Granted")
                getLocation()
            } else {
                showToast("Permission Declined")

            }
        }
        //TODO: Handle location permission result to get location on permission granted
    }

    private fun checkLocationPermissions(): Boolean {
        return if (isPermissionGranted()) {
            true
        } else {
            requestLocationPermission()
            false
        }
    }

    private fun requestLocationPermission() {
        if (this.shouldShowRequestPermissionRationaleCompat(Manifest.permission.ACCESS_FINE_LOCATION)!!) {
            requestPermissionsCompat(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST_LOCATION)
            showToast("Location Permission Is Required")
        } else {
            requestPermissionsCompat(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_LOCATION)
            showToast("Location Permission Is Not Available")

        }
    }

    private fun isPermissionGranted(): Boolean {
        return checkSelfPermissionCompat(Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
    }


    @SuppressLint("MissingPermission")
    private fun getLocation() {
        try {
            val locationRequest = LocationRequest()
            locationRequest.setInterval(10000)
            locationRequest.setFastestInterval(3000)
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

            val locationData = activity?.applicationContext?.let { LocationServices.getFusedLocationProviderClient(it) }

            locationData?.requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(result: LocationResult?) {
                    super.onLocationResult(result)
                    if (result != null && result.locations.size > 0) {
                        val latestLocationIndex = result.locations.size - 1
                        val address = geoCodeLocation(result.locations[latestLocationIndex])
                        fragmentRepresentativeBinding.scrollableHeader.address = address
                        if (firstTime) {
                            getRepList(address.toFormattedString())
                        }

                    }
                }
            }, Looper.getMainLooper())
            

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun geoCodeLocation(location: Location): Address {
        val geocoder = Geocoder(activity?.applicationContext, Locale.getDefault())

        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
                .map { address ->
                    Address(address.thoroughfare, address.subThoroughfare, address.locality, address.adminArea, address.postalCode)
                }
                .first()
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

}