package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.getViewModelFactory
import timber.log.Timber

class ElectionsFragment : Fragment() {

    private val electionsViewModel by viewModels<ElectionsViewModel> { getViewModelFactory() }

    lateinit var fragmentElectionBinding: FragmentElectionBinding

    private lateinit var electionListAdapter: ElectionListAdapter

    private lateinit var followingListAdapter: ElectionListAdapter


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentElectionBinding = FragmentElectionBinding.inflate(inflater, container, false).apply {
            electionViewModel = electionsViewModel
        }
        return fragmentElectionBinding.root;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragmentElectionBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
    }

    private fun setupListAdapter() {
        val viewModel = fragmentElectionBinding.electionViewModel
        if (viewModel != null) {
            electionListAdapter = ElectionListAdapter {
                findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(it))
            }
            fragmentElectionBinding.upcomingElectionRecyclerView.adapter = electionListAdapter

            followingListAdapter = ElectionListAdapter {
                findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(it))
            }
            fragmentElectionBinding.savedElectionRecyclerView.adapter = followingListAdapter

            //Observer for ProgressBar
            electionsViewModel.electionListItems.observe(viewLifecycleOwner, Observer {
                if (it.isEmpty()) {
                    fragmentElectionBinding.upcomingElectionProgressbar.visibility = View.VISIBLE
                } else {
                    fragmentElectionBinding.upcomingElectionProgressbar.visibility = View.GONE

                }
            })
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }

    }
}