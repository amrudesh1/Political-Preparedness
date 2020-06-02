package com.example.android.politicalpreparedness

/**
 * Extension functions for Fragment.
 */

import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.android.politicalpreparedness.network.ElectionRepository

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val repository = ElectionRepository()
    return ViewModelFactory(repository, this)
}

fun Fragment.checkSelfPermissionCompat(permission: String) =
        ActivityCompat.checkSelfPermission(activity!!.applicationContext, permission)

fun Fragment.shouldShowRequestPermissionRationaleCompat(permission: String) =
        activity?.let { ActivityCompat.shouldShowRequestPermissionRationale(it, permission) }

fun Fragment.requestPermissionsCompat(permissionsArray: Array<String>,
                                      requestCode: Int) {
    activity?.let { ActivityCompat.requestPermissions(it, permissionsArray, requestCode) }
}