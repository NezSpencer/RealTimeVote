package com.nezspencer.realtimevote.castVote

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.nezspencer.realtimevote.AppActivity
import com.nezspencer.realtimevote.State
import com.nezspencer.realtimevote.auth.AppViewModel
import com.nezspencer.realtimevote.createElection.AddContestantAdapter
import com.nezspencer.realtimevote.databinding.LayoutElectionBinding
import com.nezspencer.realtimevote.getAppPrettyDate
import com.nezspencer.realtimevote.getUserEmail
import com.nezspencer.realtimevote.model.Election


class ElectionDetailFragment : Fragment(), AddContestantAdapter.ContestantSelectionListener {

    private lateinit var appActivity: AppActivity
    private lateinit var binding: LayoutElectionBinding
    private lateinit var election: Election
    private lateinit var viewModel: AppViewModel
    private lateinit var contestantAdapter: AddContestantAdapter
    private var contestantId = ""

    companion object {
        @JvmStatic
        fun newInstance(election: Election) = ElectionDetailFragment().apply {
            this.election = election
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LayoutElectionBinding.inflate(inflater)
        appActivity.run {
            viewModel = getViewModel()
        }
        viewModel.electionUploadStatus.observe(this, Observer {
            it?.let {

                if (it.state == State.LOADED) {
                    appActivity.supportFragmentManager.popBackStack()
                    Toast.makeText(appActivity, "Voted", Toast.LENGTH_SHORT).show()
                } else if (it.state == State.FAILED) {
                    Toast.makeText(appActivity, "Failed. Try again", Toast.LENGTH_SHORT).show()
                }

            }
        })
        contestantAdapter = AddContestantAdapter(election.contestants, false, this)
        binding.rvVoteList.adapter = contestantAdapter
        binding.etElectionSeat.setText(election.electoralSeat)
        binding.etElectionSeat.isEnabled = false
        binding.etElectionSummary.setText(election.title)
        binding.etElectionSummary.isEnabled = false
        binding.btnEndDate.visibility = View.GONE
        binding.tvEndDate.text = getAppPrettyDate(appActivity, election.endDate)
        binding.fabVoteAction.setOnClickListener {
            viewModel.voteContestant(getUserEmail(appActivity), election.id, contestantId)
        }
        return binding.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is AppActivity)
            appActivity = context
    }

    override fun onContestantSelected(contestantId: String) {
        this.contestantId = contestantId
    }
}