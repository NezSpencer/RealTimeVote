package com.nezspencer.realtimevote.createElection

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.nezspencer.realtimevote.*
import com.nezspencer.realtimevote.auth.AppViewModel
import com.nezspencer.realtimevote.databinding.FragmentCreateElectionBinding
import com.nezspencer.realtimevote.model.Election

class FragmentCreateElection : Fragment() {

    private lateinit var activity: AppActivity
    private lateinit var binding: FragmentCreateElectionBinding
    private lateinit var electionAdapter: AddElectionAdapter
    private lateinit var viewModel: AppViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateElectionBinding.inflate(layoutInflater)
        electionAdapter = AddElectionAdapter(activity)
        binding.rvVoteList.adapter = electionAdapter
        activity.run {
            viewModel = ViewModelProviders.of(this)[AppViewModel::class.java]
        }
        viewModel.electionUploadStatus.observe(this, Observer<Status> {
            if (it == null) { /* do nothing*/ return@Observer
            }
            when (it.state) {
                State.LOADING -> {
                    showLoadingProgress()
                }
                State.FAILED -> {
                    showError(it.msg!!)
                }
                State.LOADED -> {
                    returnToPreviousScreen()
                }
                else -> {/*do nothing*/
                }
            }
        })

        binding.cardAddNewElection.setOnClickListener {
            val election = Election()
            val modifiedEmail = getUserEmail(activity).removeDots()
            Log.e("test", modifiedEmail)
            electionAdapter.refreshList(election)
        }
        binding.fabVoteAction.setOnClickListener {
            viewModel.createElection(getUserEmail(activity))
        }
        return binding.root
    }

    private fun returnToPreviousScreen() {
        Toast.makeText(activity, "Election uploaded", Toast.LENGTH_SHORT).show()
        viewModel.resetUploadStatus()
        activity.supportFragmentManager.popBackStack()
    }
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is AppActivity)
            activity = context
    }

    private fun showError(error: String) = Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()

    private fun showLoadingProgress() {}
}