package com.nezspencer.realtimevote.castVote

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nezspencer.realtimevote.databinding.ContestantItemBinding

class ContestantsAdapter(private val contestants: List<String>) : RecyclerView
.Adapter<ContestantsAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ContestantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount() = contestants.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemBinding.etContestantName.setText(contestants[holder
                .adapterPosition])
        holder.itemBinding.etContestantName.isEnabled = false
    }

    inner class Holder(binding: ContestantItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemBinding: ContestantItemBinding = binding
    }
}