package com.nezspencer.realtimevote.createElection

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nezspencer.realtimevote.App.Companion.elections
import com.nezspencer.realtimevote.databinding.BallotItemBinding


class AddElectionAdapter(private var context: Context) : RecyclerView.Adapter<AddElectionAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(BallotItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return elections.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.ballotItemBinding.rvContestants.adapter = AddContestantAdapter(elections[position].contestants)

    }

    inner class Holder(var ballotItemBinding: BallotItemBinding) : RecyclerView.ViewHolder
    (ballotItemBinding.root)
}