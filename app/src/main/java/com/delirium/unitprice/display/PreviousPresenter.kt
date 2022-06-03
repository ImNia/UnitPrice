package com.delirium.unitprice.display

import androidx.lifecycle.ViewModel
import com.delirium.unitprice.CallbackDB
import com.delirium.unitprice.model.FinalValue
import com.delirium.unitprice.model.ModelDB

class PreviousPresenter: ViewModel(), CallbackDB {
    private val modelDB = ModelDB(this)
    private var viewPrevious: PreviousFragment? = null

    private var currentData = listOf<FinalValue>()

    fun initPresenter(viewPrevious: PreviousFragment) {
        this.viewPrevious = viewPrevious
        modelDB.getAllFinalValue()
    }

    fun drawAllData() {
        viewPrevious?.drawCurrentData(currentData)
    }

    fun checkDataInDB() {
        modelDB.getAllFinalValue()
    }

    override fun successful() {
        TODO("Not yet implemented")
    }

    override fun failed() {
        TODO("Not yet implemented")
    }

    override fun successfulGetAll(allData: List<FinalValue>) {
        currentData = allData
        drawAllData()
    }
}