package com.nezspencer.realtimevote.createElection

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nezspencer.realtimevote.App
import com.nezspencer.realtimevote.Contestant
import com.nezspencer.realtimevote.databinding.ContestantEmptyBinding
import com.nezspencer.realtimevote.databinding.ContestantItemBinding

class AddContestantAdapter(private val PARENT_POSITION: Int) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var contestants: ArrayList<Contestant> = App.elections[PARENT_POSITION].contestants
    private val ITEM_FOOTER: Int = 0
    private val ITEM_CONTENT: Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_CONTENT) {
            val binding: ContestantItemBinding = ContestantItemBinding
                    .inflate(LayoutInflater.from(parent.context))
            return ViewHolder(binding)
        }
        val binding: ContestantEmptyBinding = ContestantEmptyBinding
                .inflate(LayoutInflater.from(parent.context))
        return FooterHolder(binding)
    }

    override fun getItemCount(): Int {
        return contestants.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.adapterPosition == contestants.size) {
            val footerHolder: FooterHolder = holder as FooterHolder
            footerHolder.itemBinding.tvEmptyView.setOnClickListener { view ->
                run {
                    contestants.add(Contestant())
                    notifyItemInserted(contestants.size)
                }
            }
        } else {
            val contentHolder: ViewHolder = holder as ViewHolder
            contentHolder.itemBinding.etContestantName.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    contestants[holder.adapterPosition].name = p0.toString()
                }
            })
            contentHolder.itemBinding.etContestantName.setText(contestants[contentHolder
                    .adapterPosition].name)


        }

    }

    override fun getItemViewType(position: Int): Int {
        if (position == contestants.size)
            return ITEM_FOOTER
        return ITEM_CONTENT
    }

    inner class ViewHolder( binding: ContestantItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val itemBinding : ContestantItemBinding = binding
    }

    inner class FooterHolder(binding: ContestantEmptyBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val itemBinding: ContestantEmptyBinding = binding
    }
}
