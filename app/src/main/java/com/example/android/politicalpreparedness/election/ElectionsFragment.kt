package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding

class ElectionsFragment : Fragment() {

    //TODO: Declare ViewModel

    lateinit var electionsViewModel: ElectionsViewModel
    lateinit var fragmentElectionBinding: FragmentElectionBinding
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fragmentElectionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_election, container, false)
        fragmentElectionBinding.lifecycleOwner = this
        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values

        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters

        //TODO: Populate recycler adapters


        return fragmentElectionBinding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        electionsViewModel = activity?.let { ViewModelProvider(it).get(ElectionsViewModel::class.java) }!!
        electionsViewModel.electionLiveData.observe(viewLifecycleOwner, Observer {
            Log.i("ElectionData", it.elections.size.toString())
        })
    }

    //TODO: Refresh adapters when fragment loads

}