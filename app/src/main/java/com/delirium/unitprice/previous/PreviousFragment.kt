package com.delirium.unitprice.previous

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.delirium.unitprice.R
import com.delirium.unitprice.databinding.PreviousResultsFragmentBinding
import com.delirium.unitprice.model.FinalValue
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PreviousFragment : Fragment(), ClickResultItem{
    private val previousPresenter: PreviousPresenter by activityViewModels()
    private lateinit var previousAdapter: PreviousAdapter
    private var recyclerView: RecyclerView? = null
    private lateinit var linearManager: LinearLayoutManager

    private lateinit var callback: CustomItemTouchHelperCallback
    private lateinit var touchHelper: ItemTouchHelper

    lateinit var bindingDisplay: PreviousResultsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingDisplay = PreviousResultsFragmentBinding.inflate(inflater, container, false)

        linearManager = LinearLayoutManager(activity)
        recyclerView = bindingDisplay.recyclerPreviousResult
        recyclerView?.layoutManager = linearManager

        return bindingDisplay.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        previousAdapter = PreviousAdapter(this)
        recyclerView?.adapter = previousAdapter
        previousPresenter.initPresenter(this)

        callback = CustomItemTouchHelperCallback(previousAdapter)
        touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)

        val fab = activity?.findViewById<FloatingActionButton>(R.id.buttonAppBar)
        fab?.show()

    }


    fun drawCurrentData(dataForDrawing: List<FinalValue>) {
        previousAdapter.dataSet = addFinalValue(dataForDrawing).toMutableList()
        previousAdapter.notifyDataSetChanged()
    }

    private fun addFinalValue(receiveData: List<FinalValue>) : List<FinalValue> {
        val fullData = mutableListOf<FinalValue>()
        for(item in receiveData) {
            fullData.add(item)
        }
        return fullData.toList()
    }

    override fun clickOnResult() {
        Log.i("PREVIOUS", "On ClickOnResult method")
        //TODO("Not yet implemented")
    }
}