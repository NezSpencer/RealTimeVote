package com.nezspencer.realtimevote.castVote

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nezspencer.realtimevote.databinding.BallotItemBinding
import com.nezspencer.realtimevote.model.Election

class ElectionListAdapter(private val list: List<Election>) : RecyclerView.Adapter<ElectionListAdapter
.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = BallotItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.etElectoralPosition.setText(list[holder.adapterPosition].electoralSeat)
        holder.binding.etElectoralPosition.isEnabled = false
        holder.binding.rvContestants.adapter = ContestantsAdapter(list[holder.adapterPosition].contestants)
    }

    inner class Holder(val binding: BallotItemBinding) : RecyclerView.ViewHolder(binding.root)
}