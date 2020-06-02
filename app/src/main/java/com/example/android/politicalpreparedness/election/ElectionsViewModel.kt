package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.ElectionRepository
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(private val electionRepository: ElectionRepository) : ViewModel() {
    private val _val = MutableLiveData<List<Election>>()
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val _followingElections = MutableLiveData<List<Election>>()


    init {
        getElectionData()
        getFollowingElections()
    }


    private fun getElectionData() {
        viewModelScope.launch {
            val data = electionRepository.getElections()
            _val.value = data.elections
        }
    }

    private fun getFollowingElections(): LiveData<List<Election>> {
        viewModelScope.launch {
            electionRepository.getAllElection().collect {
                _followingElections.postValue(it)
            }
        }
        return _followingElections
    }

    val electionListItems: LiveData<List<Election>> = _val

    val followingListItems: LiveData<List<Election>> = _followingElections


}