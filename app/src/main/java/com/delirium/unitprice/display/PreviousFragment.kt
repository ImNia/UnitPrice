package com.delirium.unitprice.display

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.delirium.unitprice.databinding.PreviousResultsFragmentBinding
import com.delirium.unitprice.model.FinalValue

class PreviousFragment : Fragment() {
    private val previousPresenter: PreviousPresenter by activityViewModels()
    private lateinit var previousAdapter: PreviousAdapter
    private var recyclerView: RecyclerView? = null
    private lateinit var linearManager: LinearLayoutManager

    lateinit var bindingDisplay: PreviousResultsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingDisplay = PreviousResultsFragmentBinding.inflate(inflater, container, false)

        linearManager = LinearLayoutManager(activity)
        recyclerView = bindingDisplay.recyclerNewsList
        recyclerView?.layoutManager = linearManager
        return bindingDisplay.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousAdapter = PreviousAdapter()
        recyclerView?.adapter = previousAdapter

        val tmp = FinalValue(100, 100, null, 200)
        previousAdapter.dataSet = listOf(tmp)
        previousAdapter.notifyDataSetChanged()
    }
}