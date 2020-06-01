package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.getViewModelFactory
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber

class VoterInfoFragment : Fragment() {

    lateinit var fragmentVoterInfoBinding: FragmentVoterInfoBinding

    lateinit var election: Election

    lateinit var state: State

    var isFollowing = false

    private val voterInfoViewModel by viewModels<VoterInfoViewModel> { getViewModelFactory() }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentVoterInfoBinding = FragmentVoterInfoBinding.inflate(inflater, container, false)
        election = VoterInfoFragmentArgs.fromBundle(arguments!!).election
        fragmentVoterInfoBinding.voterViewModel = voterInfoViewModel


        voterInfoViewModel.getVoterInfoData(election.id, election.division.state)
                .observe(viewLifecycleOwner, Observer {
                    if (it.isEmpty()) {
                        fragmentVoterInfoBinding.voterInfoProgress.visibility = View.VISIBLE
                    } else {
                        fragmentVoterInfoBinding.voterInfoProgress.visibility = View.GONE
                        state = it[0]
                        fragmentVoterInfoBinding.state = state

                    }
                })

        fragmentVoterInfoBinding.election = election

        voterInfoViewModel.intentData.observe(viewLifecycleOwner, Observer {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(it)
            startActivity(intent)
        })

        voterInfoViewModel.isFollowing(election.id).observe(viewLifecycleOwner, Observer {
            if (it == 1) {
                isFollowing = true
                fragmentVoterInfoBinding.followElectionButton.text = "UNFOLLOW"
            } else {
                isFollowing = false
                fragmentVoterInfoBinding.followElectionButton.text = "FOLLOW"
            }
        })

        fragmentVoterInfoBinding.followElectionButton.setOnClickListener {
            if (!isFollowing) {
                isFollowing = true
                fragmentVoterInfoBinding.followElectionButton.text = "UNFOLLOW"
                voterInfoViewModel.followElection(election)
            } else {
                isFollowing = false
                fragmentVoterInfoBinding.followElectionButton.text = "FOLLOW"
                voterInfoViewModel.deleteElection(election)
            }
        }




        return fragmentVoterInfoBinding.root;

    }
//TODO: Add ViewModel values and create ViewModel

    //TODO: Add binding values

    //TODO: Populate voter info -- hide views without provided data.
    /**
    Hint: You will need to ensure proper data is provided from previous fragment.
     */


    //TODO: Handle loading of URLs

    //TODO: Handle save button UI state
    //TODO: cont'd Handle save button clicks

    //TODO: Create method to load URL intents
    fun log(message: String) {
        Timber.tag("VoterInfo").i(message)
    }
}