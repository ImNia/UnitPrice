package com.delirium.unitprice.previous

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.delirium.unitprice.R
import com.delirium.unitprice.databinding.PreviousResultsFragmentBinding
import com.delirium.unitprice.model.FinalValue
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.util.*

class PreviousFragment : Fragment(), ClickResultItem {
    private val previousPresenter: PreviousPresenter by activityViewModels()
    private lateinit var previousAdapter: PreviousAdapter
    private var recyclerView: RecyclerView? = null
    private lateinit var linearManager: LinearLayoutManager

    private lateinit var callback: CustomItemTouchHelperCallback
    private lateinit var touchHelper: ItemTouchHelper

    lateinit var bindingDisplay: PreviousResultsFragmentBinding

    private var snackBar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingDisplay = PreviousResultsFragmentBinding.inflate(inflater, container, false)

        linearManager = LinearLayoutManager(activity)
        recyclerView = bindingDisplay.recyclerPreviousResult
        recyclerView?.layoutManager = linearManager

        setHasOptionsMenu(true)
        val toolbar: MaterialToolbar? = activity?.findViewById(R.id.toolBar)
        toolbar?.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuMore -> {
                    Log.i("PREVIOUS", "Click on MORE")
                    true
                }
                R.id.menuAdded -> {
                    bindingDisplay.root.findNavController().navigate(
                        R.id.action_previousPrice_to_newCalculation
                    )
                    true
                }
                R.id.menuUpdate -> {
                    previousPresenter.updateData()
                    true
                }
                else -> {
                    false
                }
            }
        }

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

    fun goToDescription(item: FinalValue) {
        bindingDisplay.root.findNavController().navigate(
            PreviousFragmentDirections.actionPreviousPriceToDescription(item)
        )
    }

    override fun clickOnResultDelete(idCard: String) {
        Log.i("PREVIOUS", "On ClickOnResult method")
        previousPresenter.clickOnDelete(idCard)
    }

    override fun clickOnResultDescription(idCard: String) {
        previousPresenter.clickOnDescription(idCard)
    }

    override fun deleteCard(finalValue: FinalValue) {
        previousPresenter.swipeOnDelete(finalValue)
    }

    override fun changePlaceCards(newList: List<FinalValue>) {
        previousPresenter.changeHierarchy(newList)
    }

    /**** SnackBar ****/
    fun snackBarWithError() {
        val textError = getString(R.string.not_deleted)
        snackBar = Snackbar
            .make(bindingDisplay.linearLayout2, textError, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.ok_error) {
                hideSnackBar()
            }
        snackBar?.show()
    }

    fun hideSnackBar() {
        snackBar?.let {
            if (it.isShown) it.dismiss()
        }
    }
}