package com.example.android.politicalpreparedness.database

import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.flow.Flow

@Dao
interface ElectionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertElection(election: Election)

    @Delete
    suspend fun deleteElection(election: Election)

    @Query("SELECT COUNT(*) from election_table WHERE id =:id")
    fun isFollowing(id: Int): Flow<Int>

    @Query("SELECT * from election_table")
    fun getAllElection(): Flow<List<Election>>


    //TODO: Add select all election query
    //TODO: Add select single election query

    //TODO: Add delete query

    //TODO: Add clear query

}