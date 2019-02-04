package com.nezspencer.realtimevote.createElection

import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import com.nezspencer.realtimevote.AppActivity
import com.nezspencer.realtimevote.State
import com.nezspencer.realtimevote.Status
import com.nezspencer.realtimevote.auth.AppViewModel
import com.nezspencer.realtimevote.databinding.FragmentCreateElectionBinding
import com.nezspencer.realtimevote.getUserEmail
import com.nezspencer.realtimevote.model.Contestant
import com.nezspencer.realtimevote.model.Election
import java.util.*

class FragmentCreateElection : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var activity: AppActivity
    private lateinit var binding: FragmentCreateElectionBinding
    private lateinit var viewModel: AppViewModel
    private lateinit var contestantAdapter: AddContestantAdapter
    private val contestants = mutableListOf(Contestant())
    private var electoralSeat = ""
    val calendar = Calendar.getInstance()
    private val datePickerDialog by lazy {
        DatePickerDialog(activity, this, calendar[Calendar.YEAR],
                calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH])
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateElectionBinding.inflate(layoutInflater)
        contestantAdapter = AddContestantAdapter(contestants)
        binding.rvVoteList.adapter = contestantAdapter
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

        binding.tvAddNewElection.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let { electoralSeat = it.toString() }
            }
        })
        binding.fabVoteAction.setOnClickListener {
            viewModel.createElection(getUserEmail(activity), Election(electoralSeat, contestants,
                    calendar.timeInMillis))
        }

        binding.btnEndDate.setOnClickListener { showDatePicker() }
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

    private fun showDatePicker() {
        datePickerDialog.show()
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        calendar[Calendar.YEAR] = p1
        calendar[Calendar.MONTH] = p2
        calendar[Calendar.DAY_OF_MONTH] = p3 + 1
        binding.btnEndDate.text = "$p3 ${calendar.getDisplayName(Calendar.MONTH, Calendar.LONG,
                Locale.ENGLISH)}, $p1"
    }
}