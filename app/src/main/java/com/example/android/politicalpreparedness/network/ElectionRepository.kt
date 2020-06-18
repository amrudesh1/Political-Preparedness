package com.example.android.politicalpreparedness.network

import com.example.android.politicalpreparedness.ElectionApplication
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.models.*
import com.example.android.politicalpreparedness.util.Resource
import kotlinx.coroutines.Deferred

class ElectionRepository {
    private val databaseInstance = ElectionDatabase.getInstance(ElectionApplication.instance)
    private val electionDao: ElectionDao = databaseInstance.electionDao

    suspend fun saveElection(election: Election) =
            electionDao.insertElection(election)

    suspend fun deleteElection(election: Election) =
            electionDao.deleteElection(election)

    fun isFollowing(id: Int) =
            electionDao.isFollowing(id)

    fun getAllElection() = electionDao.getAllElection()

    fun getRepresentativesDeferred(address: String) = CivicsApi.retrofitService.getRepresentatives(address)


    fun getElections(): Deferred<ElectionResponse> = CivicsApi.retrofitService.getElections()


    fun getVoterInfo(id: Int, address: String) = CivicsApi.retrofitService.getVoterInfo(id, address)

}