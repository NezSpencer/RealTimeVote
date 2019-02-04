package com.nezspencer.realtimevote.castVote

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.DatabaseError
import com.nezspencer.realtimevote.AppActivity
import com.nezspencer.realtimevote.databinding.FragmentCastVoteBinding
import com.nezspencer.realtimevote.model.Election

class CastVoteFragment : Fragment() {

    private lateinit var baseActivity: AppActivity
    private lateinit var binding: FragmentCastVoteBinding
    private val elections = ArrayList<Election>()

    companion object {
        fun newInstance() = CastVoteFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCastVoteBinding.inflate(inflater)
        val adapter = ElectionListAdapter(baseActivity, elections)
        binding.rvBallots.adapter = adapter
        baseActivity.run {

            getViewModel().electionData.observe(this, Observer<Pair<DatabaseError?,
                    List<Election>>> {
                if (it == null)
                    return@Observer

                if (it.first != null) {
                    Toast.makeText(baseActivity, "Error", Toast.LENGTH_SHORT).show()
                } else {
                    elections.clear()
                    elections.addAll(it.second)
                    adapter.notifyDataSetChanged()
                }

            })
        }
        return binding.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is AppActivity)
            baseActivity = context
    }
}