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

    private fun drawResultValue(result: Double, operation: String) {
        viewCalculate?.drawResultCalculate(result.toString(), operation)
    }

    fun switchOperation(operation: String) {
        currentOperation = operation
        prepareAndDrawView(operation)
    }

    fun callCalculate(operation: String, firstValue: String?, secondValue: String?, thirdValue: String?) {
        val terms = listOf(firstValue, secondValue, thirdValue).filterNotNull()
        if (firstValue.isNullOrEmpty() || secondValue.isNullOrEmpty()) {
            viewCalculate?.snackBarWithError()
        } else {
            val result = calculateValue(operation, terms)
            drawResultValue(result, operation)
        }
    }

    private fun calculateValue(operation: String, terms: List<String>) : Double {
        val operations = getMapOperations()
        return when (operation) {
             operations[AvailableOperations.PRICE_FOR_KG] -> {
                1000 / terms[0].toDouble() * terms[1].toDouble()
            }
            operations[AvailableOperations.KNOWING_PRICE_FOR_KG] -> {
                terms[1].toDouble() / 1000 * terms[0].toDouble()
            }
            operations[AvailableOperations.COUNT_FOR_1_KG] -> {
                1000 / terms[0].toDouble() * terms[1].toDouble()
            }
            operations[AvailableOperations.PRICE_DEFINITE_WEIGHT] -> {
                terms[0].toDouble() / terms[1].toDouble() * terms[2].toDouble()
            }
            else -> {
                -1.0
            }
        }
    }

    /*** Request to DataBase ***/

    fun saveResult(
        operation: String,
        result: String,
        firstValue: String,
        secondValue: String,
        name: String = "Untitled"
    ) {
        val finalValue = FinalValue(
            UUID.randomUUID(),
            firstValue.toLong(),
            secondValue.toLong(),
            null,
            null,
            operation,
            result,
            name
        )
        modelDB.insertFinalValue(finalValue)
    }

    fun getOperations() : List<String> {
        val operations = modelDB.getOperations()
        return operations.values.toList()
    }

    private fun getMapOperations() : Map<AvailableOperations, String> {
        return modelDB.getOperations()
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