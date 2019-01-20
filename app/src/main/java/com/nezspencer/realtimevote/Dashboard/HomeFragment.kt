package com.nezspencer.realtimevote.Dashboard

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nezspencer.realtimevote.AppActivity
import com.nezspencer.realtimevote.R
import com.nezspencer.realtimevote.castVote.CastVoteFragment
import com.nezspencer.realtimevote.createElection.FragmentCreateElection
import com.nezspencer.realtimevote.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var dashboard: AppActivity
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.btnCreateVote.setOnClickListener { openFragment(FragmentCreateElection()) }
        binding.btnVote.setOnClickListener { openFragment(CastVoteFragment.newInstance()) }
        return binding.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is AppActivity)
            dashboard = context
    }

    private fun openFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.replace(R.id.main_frame, fragment, fragment.javaClass.name)
                ?.addToBackStack(fragment.javaClass.name)?.commit()
    }
}