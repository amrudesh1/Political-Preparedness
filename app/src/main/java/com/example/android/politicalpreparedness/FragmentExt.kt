package com.example.android.politicalpreparedness

/**
 * Extension functions for Fragment.
 */

import androidx.fragment.app.Fragment
import com.example.android.politicalpreparedness.network.ElectionRepository

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val repository = ElectionRepository()
    return ViewModelFactory(repository, this)
}
