package com.nezspencer.realtimevote.createElection

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nezspencer.realtimevote.Contestant
import com.nezspencer.realtimevote.databinding.ContestantItemBinding

class AddContestantAdapter(var contestants: ArrayList<Contestant>) :
        RecyclerView.Adapter<AddContestantAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : ContestantItemBinding = ContestantItemBinding
                .inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return contestants.size + 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (contestants.size == holder.adapterPosition || position == contestants.size) {
            holder.itemBinding.contestantItem.visibility = View.GONE
            holder.itemBinding.tvEmptyView.visibility = View.VISIBLE
        }
        else{
            holder.itemBinding.contestantItem.visibility = View.VISIBLE
            holder.itemBinding.tvEmptyView.visibility = View.GONE
        }
        holder.itemBinding.tvEmptyView.setOnClickListener({ view ->
            run {
                if (contestants.size == 0)
                    contestants.add(Contestant())

                holder.itemBinding.contestantItem.visibility = View.VISIBLE
                holder.itemBinding.tvEmptyView.visibility = View.GONE
            }
        })
    }

    inner class ViewHolder( binding: ContestantItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val itemBinding : ContestantItemBinding = binding
    }
}
