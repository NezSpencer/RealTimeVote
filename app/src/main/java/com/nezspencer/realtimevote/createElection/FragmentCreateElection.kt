package com.nezspencer.realtimevote.createElection

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nezspencer.realtimevote.App
import com.nezspencer.realtimevote.Election
import com.nezspencer.realtimevote.auth.AuthActivity
import com.nezspencer.realtimevote.databinding.FragmentCreateElectionBinding

class FragmentCreateElection : Fragment() {

    private lateinit var activity: AuthActivity
    private lateinit var binding: FragmentCreateElectionBinding
    private lateinit var electionAdapter: AddElectionAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateElectionBinding.inflate(layoutInflater)
        electionAdapter = AddElectionAdapter(activity)
        binding.rvVoteList.adapter = electionAdapter

        binding.fabVoteAction.setOnClickListener({
            App.elections.add(Election())
            electionAdapter.notifyDataSetChanged()
        })
        return binding.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is AuthActivity)
            activity = context
    }
}