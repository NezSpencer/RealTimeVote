package com.nezspencer.realtimevote.createElection

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nezspencer.realtimevote.databinding.ContestantEmptyBinding
import com.nezspencer.realtimevote.databinding.ContestantItemBinding
import com.nezspencer.realtimevote.model.Contestant
import java.util.*

class AddContestantAdapter(private val contestants: MutableList<Contestant>,
                           private val isEditable: Boolean = true,
                           private val listener: ContestantSelectionListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemFooter: Int = 0
    private val itemContent: Int = 1
    private var checkedPosition = -1
    private val selections = BitSet(contestants.size)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == itemContent) {
            val binding: ContestantItemBinding = ContestantItemBinding
                    .inflate(LayoutInflater.from(parent.context))
            return ViewHolder(binding)
        }
        val binding: ContestantEmptyBinding = ContestantEmptyBinding
                .inflate(LayoutInflater.from(parent.context))
        return FooterHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (isEditable) contestants.size + 1 else contestants.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        if (pos == contestants.size) {
            val footerHolder: FooterHolder = holder as FooterHolder
            footerHolder.itemBinding.tvEmptyView.setOnClickListener {
                run {
                    contestants.add(Contestant())
                    notifyItemInserted(contestants.size)
                }
            }
        } else {
            val contentHolder: ViewHolder = holder as ViewHolder
            val contestant = contestants[pos]
            if (isEditable) {
                contentHolder.itemBinding.etContestantName.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        contestants[pos].publicName = p0.toString()
                    }
                })

                contentHolder.itemBinding.etContestantName.setText(contestant.publicName)
            } else {
                contentHolder.itemBinding.etContestantName.visibility = View.GONE
                if (selections.get(contentHolder.adapterPosition) && pos != checkedPosition)
                    selections.clear(pos)
                contentHolder.itemBinding.rbContestant
                        .setOnCheckedChangeListener { _, isChecked ->
                            if (isChecked) {
                                checkedPosition = pos
                                selections.set(checkedPosition)
                                listener.onContestantSelected(contestant.id)
                                notifyDataSetChanged()
                            }
                        }

                contentHolder.itemBinding.rbContestant.text = contestant.publicName

            }




        }

    }

    override fun getItemViewType(position: Int): Int {
        if (position == contestants.size)
            return itemFooter
        return itemContent
    }

    inner class ViewHolder( binding: ContestantItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val itemBinding : ContestantItemBinding = binding
    }

    inner class FooterHolder(binding: ContestantEmptyBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val itemBinding: ContestantEmptyBinding = binding
    }

    interface ContestantSelectionListener {
        fun onContestantSelected(contestantId: String)
    }
}
