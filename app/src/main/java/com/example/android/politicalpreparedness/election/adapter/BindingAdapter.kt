package com.example.android.politicalpreparedness.election.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.util.SpacesItemDecoration
import java.util.*

@BindingAdapter("app:setAdapter")
fun setElectionAdapter(recyclerView: RecyclerView, items: List<Election>?) {
    items?.let {
        (recyclerView.adapter as ElectionListAdapter).submitList(it)
    }
    recyclerView.apply {
        layoutManager = LinearLayoutManager(recyclerView.context)
        addItemDecoration(SpacesItemDecoration(10))
    }
}


@BindingAdapter("setDate")
fun setElectionDate(textView: TextView, date: Date) {
    textView.text = date.time.toString()
}
