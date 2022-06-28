package com.delirium.unitprice.previous

import androidx.lifecycle.ViewModel
import com.delirium.unitprice.CallbackDB
import com.delirium.unitprice.model.FinalValue
import com.delirium.unitprice.model.ModelDB
import java.util.*

class PreviousPresenter: ViewModel(), CallbackDB {
    private val modelDB = ModelDB(this)
    private var viewPrevious: PreviousFragment? = null

    private var currentData = listOf<FinalValue>()

    fun initPresenter(viewPrevious: PreviousFragment) {
        this.viewPrevious = viewPrevious
        modelDB.getAllFinalValue()

        modelDB.realmDB.addChangeListener {
            modelDB.getAllFinalValue()
        }
    }

    private fun drawAllData() {
        viewPrevious?.drawCurrentData(currentData)
    }

    fun clickOnDelete(idCard: String) {
        modelDB.deleteFinalValue(UUID.fromString(idCard))
        checkDataInDB()
    }

    fun swipeOnDelete(finalValue: FinalValue) {
        finalValue.id?.let { modelDB.deleteFinalValue(it) }
        checkDataInDB()
    }

    private fun checkDataInDB() {
        modelDB.getAllFinalValue()
    }

    fun changeHierarchy(newList: List<FinalValue>) {
        modelDB.changeHierarchy(newList)
    }

    /*** Handle callback from model ***/

    override fun successful() {
        TODO("Not yet implemented")
    }

    override fun failed() {
        viewPrevious?.snackBarWithError()
    }

    override fun successfulGetAll(allData: List<FinalValue>) {
        currentData = allData
        drawAllData()
    }
}