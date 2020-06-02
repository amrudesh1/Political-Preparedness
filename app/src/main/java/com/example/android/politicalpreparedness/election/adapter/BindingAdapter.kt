package com.example.android.politicalpreparedness.election.adapter

import android.content.Intent
import android.net.Uri
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.util.SpacesItemDecoration
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("setAdapter")
fun setElectionAdapter(recyclerView: RecyclerView, items: List<Election>?) {
    items?.let {
        (recyclerView.adapter as ElectionListAdapter).submitList(it)
    }
    recyclerView.apply {
        layoutManager = LinearLayoutManager(recyclerView.context)
        addItemDecoration(SpacesItemDecoration(10))
    }

}

@BindingAdapter("setFollowingElectionAdapter")
fun setFollowingElection(recyclerView: RecyclerView, items: List<Election>?) {
    items?.let {
        (recyclerView.adapter as ElectionListAdapter).submitList(it)
    }
    recyclerView.apply {
        layoutManager = LinearLayoutManager(recyclerView.context)
        addItemDecoration(SpacesItemDecoration(10))
    }

}

