package com.delirium.unitprice.display

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.delirium.unitprice.R
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

        setFragmentResultListener("dataForDisplay") { requestKey, bundle ->
            val result = bundle.getString("finalKey")
            Log.i("PREVIOUS_FRAGMENT", "${result}")
            previousPresenter.checkDataInDB()
        }
        previousPresenter.initPresenter(this)
    }

    fun drawCurrentData(dataForDrawing: List<FinalValue>) {
        previousAdapter.dataSet = addFinalValue(dataForDrawing)
        previousAdapter.notifyDataSetChanged()
    }

    fun addFinalValue(receiveData: List<FinalValue>) : List<FinalValue> {
        val fullData = mutableListOf<FinalValue>()
        for(item in receiveData) {
            item.finalString = when (item.operation) {
                "Price for kg" -> {
                    getString(R.string.finalPriceForKg, item.result.toString())
                }
                "Knowing price for kg" -> {
                    getString(
                        R.string.finalKnowingPriceForKg,
                        item.result.toString(),
                        item.xValue.toString()
                    )
                }
                "Count for 1kg" -> {
                    getString(
                        R.string.finalCountForKg,
                        item.finalString.toString(),
                        item.xValue.toString()
                    )
                }
                "Price definite weight" -> {
                    getString(
                        R.string.finalPriceDefiniteWeight,
                        "something",
                        "something#2"
                    )
                }
                else -> {
                    "Error"
                }
            }
            fullData.add(item)
        }
        return fullData.toList()
    }
}