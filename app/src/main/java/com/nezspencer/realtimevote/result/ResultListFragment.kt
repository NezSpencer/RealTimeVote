package com.nezspencer.realtimevote.result

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nezspencer.realtimevote.AppActivity
import com.nezspencer.realtimevote.auth.AppViewModel
import com.nezspencer.realtimevote.databinding.FragmentResultListBinding
import com.nezspencer.realtimevote.pojo.ResultItem

class ResultListFragment : Fragment() {

    private lateinit var binding: FragmentResultListBinding
    private lateinit var hostActivity: AppActivity
    private lateinit var viewModel: AppViewModel

    companion object {
        fun newInstance() = ResultListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentResultListBinding.inflate(inflater)
        val results = mutableListOf<ResultItem>()
        val adapter = ResultListAdapter(hostActivity, results)
        binding.rvBallots.adapter = adapter
        hostActivity.run {
            viewModel = getViewModel()
        }
        viewModel.resultsLiveData.observe(this, Observer { pair ->
            pair?.let {
                if (it.first != null) {

                } else {
                    results.clear()
                    results.addAll(it.second)
                    adapter.notifyDataSetChanged()


                }
            }
        })
        viewModel.seeVoteResults()
        return binding.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is AppActivity)
            hostActivity = context
    }


}