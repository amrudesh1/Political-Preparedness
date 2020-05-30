package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import com.example.android.politicalpreparedness.getViewModelFactory
import com.example.android.politicalpreparedness.network.models.Election
import timber.log.Timber

class ElectionsFragment : Fragment() {

    private val electionsViewModel by viewModels<ElectionsViewModel> { getViewModelFactory() }

    lateinit var fragmentElectionBinding: FragmentElectionBinding

    private lateinit var electionListAdapter: ElectionListAdapter

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
            electionListAdapter = ElectionListAdapter(electionsViewModel, object : ElectionListener {
                override fun onClick(election: Election) {

                }
            })
            fragmentElectionBinding.upcomingElectionRecyclerView.adapter = electionListAdapter
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }

    }
}