package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.politicalpreparedness.network.ElectionRepository
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception

class RepresentativeViewModel(val repository: ElectionRepository) : ViewModel() {

    //TODO: Establish live data for representatives and address
    val _respresentatives = MutableLiveData<List<Representative>>()


    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    //TODO: Create function to fetch representatives from API from a provided address
    fun getRepresentatives(address: String): LiveData<List<Representative>> {
        viewModelScope.launch {
            try {
                val (offices, officials) = repository.getRepresentativesDeferred(address).await()
                _respresentatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return _respresentatives
    }


    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location

    //TODO: Create function to get address from individual fields

    val representativesItems: LiveData<List<Representative>> = _respresentatives


}
