package com.nezspencer.realtimevote.createElection

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nezspencer.realtimevote.App
import com.nezspencer.realtimevote.databinding.BallotItemBinding
import com.nezspencer.realtimevote.model.Election


class AddElectionAdapter(private val context: Context) :
        RecyclerView.Adapter<AddElectionAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(BallotItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return App.elections.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.ballotItemBinding.rvContestants.adapter = AddContestantAdapter(holder.adapterPosition)
        holder.ballotItemBinding.etElectoralPosition.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                App.elections[holder.adapterPosition].electoralSeat = p0.toString()
            }
        })
    }

    fun refreshList(newItem: Election) {
        App.elections.add(newItem)
        notifyItemInserted(App.elections.size - 1)
    }

    inner class Holder(var ballotItemBinding: BallotItemBinding) : RecyclerView.ViewHolder
    (ballotItemBinding.root)
}