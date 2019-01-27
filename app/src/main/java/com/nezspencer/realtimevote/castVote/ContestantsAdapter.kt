package com.nezspencer.realtimevote.castVote

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nezspencer.realtimevote.databinding.ContestantItemBinding
import com.nezspencer.realtimevote.model.Contestant

class ContestantsAdapter(private val contestants: List<Contestant>) : RecyclerView
.Adapter<ContestantsAdapter.Holder>() {
    private var selectedPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ContestantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount() = contestants.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.etContestantName.setText(contestants[holder
                .adapterPosition].publicName)
        holder.binding.etContestantName.isEnabled = false
        holder.binding.rbContestant.isChecked = holder.adapterPosition == selectedPosition
    }

    inner class Holder(val binding: ContestantItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.rbContestant.setOnClickListener {
                selectedPosition = adapterPosition
                notifyDataSetChanged()
            }
        }
    }
}