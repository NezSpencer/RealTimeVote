package com.nezspencer.realtimevote.result

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nezspencer.realtimevote.databinding.ItemMainListBinding
import com.nezspencer.realtimevote.getAppPrettyDate
import com.nezspencer.realtimevote.pojo.ResultItem

class ResultListAdapter(private val context: Context,
                        private val resultItems: List<ResultItem>) : RecyclerView
.Adapter<ResultListAdapter.Holder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val binding = ItemMainListBinding.inflate(LayoutInflater.from(p0.context))
        return Holder(binding)
    }

    override fun getItemCount() = resultItems.size

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        val result = resultItems[p0.adapterPosition]
        p0.binding.tvSummaryText.text = result.metaData.electionTitle
        p0.binding.tvEndDate.text = getAppPrettyDate(context, result.metaData.endDate)
    }

    inner class Holder(val binding: ItemMainListBinding) : RecyclerView.ViewHolder(binding.root)
}