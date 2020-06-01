package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ElectionGlobalRecyclerviewItemBinding
import com.example.android.politicalpreparedness.network.models.Election

class ElectionListAdapter(val onItemClicked: (Election) -> Unit) : ListAdapter<Election, ElectionListAdapter.ElectionViewHolder>(ElectionDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClicked)
    }

    class ElectionViewHolder(private val electionBinding: ElectionGlobalRecyclerviewItemBinding) :
            RecyclerView.ViewHolder(electionBinding.root) {

        fun bind(election: Election, onItemClicked: (Election) -> Unit) = with(electionBinding)
        {
            electionBinding.election = election
            electionBinding.root.setOnClickListener {
                onItemClicked(election)
            }
            electionBinding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ElectionViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ElectionGlobalRecyclerviewItemBinding.inflate(layoutInflater, parent, false)

                return ElectionViewHolder(binding)
            }
        }
    }

    class ElectionDiffCallback : DiffUtil.ItemCallback<Election>() {
        override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
            return oldItem == newItem
        }

    }


}


//TODO: Bind ViewHolder

//TODO: Add companion object to inflate ViewHolder (from)

//TODO: Create ElectionViewHolder

//TODO: Create ElectionDiffCallback

//TODO: Create ElectionListener