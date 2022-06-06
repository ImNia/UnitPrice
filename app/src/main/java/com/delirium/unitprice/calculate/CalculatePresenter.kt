package com.delirium.unitprice.calculate

import android.util.Log
import androidx.lifecycle.ViewModel
import com.delirium.unitprice.AvailableOperations
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
        prepareAndDrawView()
    }

    fun detachView() {
        viewCalculate = null
    }

    private fun prepareAndDrawView(operation: String? = null) {
        val allOperation = modelDB.getOperations()
        viewCalculate?.drawView(operation ?: allOperation.values.first())
    }

    fun getOperations() : List<String> {
        val operations = modelDB.getOperations()
        return operations.values.toList()
    }

    private fun getMapOperations() : Map<AvailableOperations, String> {
        return modelDB.getOperations()
    }

    fun switchOperation(operation: String) {
        currentOperation = operation
        prepareAndDrawView(operation)
    }

    //TODO all value in one array
    fun callCalculate(operation: String, firstValue: String?, secondValue: String?) {
        if (firstValue.isNullOrEmpty() || secondValue.isNullOrEmpty()) {
            viewCalculate?.snackBarWithError()
        } else {
            val result = calculateValue(operation, firstValue.toDouble(), secondValue.toDouble())
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
    }

    private fun calculateValue(operation: String, firstValue: Double, secondValue: Double) : Double {
        val operations = getMapOperations()
        return when (operation) {
             operations[AvailableOperations.PRICE_FOR_KG] -> {
                1000 / secondValue * firstValue
            }
            operations[AvailableOperations.KNOWING_PRICE_FOR_KG] -> {
                secondValue / 1000 * firstValue
            }
            operations[AvailableOperations.COUNT_FOR_1_KG] -> {
                1000 / secondValue * firstValue
            }
            operations[AvailableOperations.PRICE_DEFINITE_WEIGHT] -> {
                //TODO need third value in view
                0.0
            }
            else -> {
                -1.0
            }
        }
    }

    /*** Handle callback from model ***/
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