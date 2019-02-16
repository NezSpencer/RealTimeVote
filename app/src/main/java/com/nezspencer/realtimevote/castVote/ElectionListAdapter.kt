package com.nezspencer.realtimevote.castVote

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nezspencer.realtimevote.databinding.ItemMainListBinding
import com.nezspencer.realtimevote.getAppPrettyDate
import com.nezspencer.realtimevote.getUserEmail
import com.nezspencer.realtimevote.model.Election

class ElectionListAdapter(private val context: Context,
                          private val list: List<Election>,
                          private val listener: ElectionItemCLickListener)
    : RecyclerView.Adapter<ElectionListAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemMainListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val election = list[holder.adapterPosition]
        holder.binding.tvSummaryText.text = election.title
        holder.binding.tvCreatedBy.text =
                if (election.creatorEmail == (getUserEmail(context))) "You"
                else election.creator
        holder.binding.tvEndDate.text = getAppPrettyDate(context, election.endDate)

        holder.binding.root.setOnClickListener {
            listener.onItemClicked(election)
        }
    }

    inner class Holder(val binding: ItemMainListBinding) : RecyclerView.ViewHolder(binding.root)

    interface ElectionItemCLickListener {
        fun onItemClicked(election: Election)
    }
}