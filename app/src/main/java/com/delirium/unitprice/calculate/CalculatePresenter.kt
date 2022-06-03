package com.delirium.unitprice.calculate

import android.util.Log
import androidx.lifecycle.ViewModel
import com.delirium.unitprice.CallbackDB
import com.delirium.unitprice.R
import com.delirium.unitprice.model.FinalValue
import com.delirium.unitprice.model.ModelDB
import java.util.*

class CalculatePresenter : ViewModel(), CallbackDB {
    private var viewCalculate: CalculateFragment? = null

    private var currentOperation: String? = null

    private val modelDB = ModelDB(this)

    fun attachView(viewCalculate: CalculateFragment) {
        this.viewCalculate = viewCalculate
    }

    fun detachView() {
        viewCalculate = null
    }

    fun drawCurrentOperation(operation: String? = null) {
        //TODO changed to main value
        viewCalculate?.drawView(operation ?: "Price for kg")
    }

    fun switchOperation(operation: String) {
        currentOperation = operation
        drawCurrentOperation(operation)
    }

    //TODO all value in one array
    fun callCalculate(operation: String, firstValue: Double, secondValue: Double) {
        val result = calculateValue(operation, firstValue, secondValue)
        Log.i("CALCULATE", "$result")
        val finalValue = FinalValue(
            UUID.randomUUID(),
            firstValue.toLong(),
            secondValue.toLong(),
            null,
            result.toLong(),
            operation
        )
        modelDB.insertFinalValue(finalValue)
    }

    private fun calculateValue(operation: String, firstValue: Double, secondValue: Double) : Double {
        return when (operation) {
            "Price for kg" -> {
                1000 / secondValue * firstValue
            }
            "Knowing price for kg" -> {
                secondValue / 1000 * firstValue
            }
            "Count for 1kg" -> {
                1000 / secondValue * firstValue
            }
            "Price definite weight" -> {
                //TODO need third value in view
                0.0
            }
            else -> {
                -1.0
            }
        }
    }

    override fun successful() {
        //TODO("Not yet implemented")
    }

    override fun failed() {
        //TODO("Not yet implemented")
    }

    override fun successfulGetAll(allData: List<FinalValue>) {
        //TODO("Not yet implemented")
    }
}