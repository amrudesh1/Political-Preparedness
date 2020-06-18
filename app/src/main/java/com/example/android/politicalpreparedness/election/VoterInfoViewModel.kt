package com.example.android.politicalpreparedness.election

import android.accounts.NetworkErrorException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.ElectionRepository
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.State
import com.example.android.politicalpreparedness.util.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.lang.Exception

class VoterInfoViewModel(val repository: ElectionRepository) : ViewModel() {

    private var viewModelJob = SupervisorJob()
    private var viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    var stateData = MutableLiveData<List<State>>()
    var intentData = MutableLiveData<String>()
    var isFollowing = MutableLiveData<Int>()


    fun getVoterInfoData(id: Int, address: String) {
        try {
            viewModelScope.launch {
                val voterInfoData = try {
                    repository.getVoterInfo(id, address).await().state
                } catch (e: Exception) {
                    emptyList<State>()
                }

                stateData.postValue(voterInfoData)
            }
        } catch (throwable: Throwable) {
            emptyList<State>()
        }
    }

    fun setBrowserIntent(url: String?) {
        if (url != null) {
            intentData.value = url
        }
    }

    fun followElection(election: Election) {
        viewModelScope.launch {
            repository.saveElection(election)
        }
    }

    fun deleteElection(election: Election) {
        viewModelScope.launch {
            repository.deleteElection(election)
        }
    }

    fun isFollowing(electionId: Int): LiveData<Int> {
        viewModelScope.launch {
            repository.isFollowing(electionId).collect {
                isFollowing.postValue(it)
            }
        }
        return isFollowing
    }

    //TODO: Add live data to hold voter info

    //TODO: Add var and methods to populate voter info

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database


    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}