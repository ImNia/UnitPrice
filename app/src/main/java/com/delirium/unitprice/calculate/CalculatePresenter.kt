package com.delirium.unitprice.calculate

import androidx.lifecycle.ViewModel
import com.delirium.unitprice.AvailableOperations
import com.delirium.unitprice.CallbackDB
import com.delirium.unitprice.model.FinalValue
import com.delirium.unitprice.model.ModelDB
import java.util.*

class CalculatePresenter : ViewModel(), CallbackDB {
    private var viewCalculate: CalculateFragment? = null

    private var currentOperation: AvailableOperations? = null

    private val modelDB = ModelDB(this)

    fun attachView(viewCalculate: CalculateFragment) {
        this.viewCalculate = viewCalculate
        prepareAndDrawView()
    }

    fun detachView() {
        viewCalculate = null
    }

    private fun prepareAndDrawView(operation: AvailableOperations? = null) {
        val allOperation = modelDB.getOperations()
        viewCalculate?.drawView(operation ?: allOperation.keys.first())
    }

    private fun drawResultValue(result: String, operation: AvailableOperations) {
        viewCalculate?.drawResultCalculate(result, operation)
    }

    fun switchOperation(operation: AvailableOperations) {
        currentOperation = operation
        prepareAndDrawView(operation)
    }

    fun callCalculate(operation: AvailableOperations, firstValue: String?, secondValue: String?, thirdValue: String?) {
        val terms = listOf(firstValue, secondValue, thirdValue).filterNotNull()
        if (firstValue.isNullOrEmpty() || secondValue.isNullOrEmpty()) {
            viewCalculate?.snackBarWithError()
        } else {
            val result = calculateValue(operation, terms)
            drawResultValue(String.format("%.2f", result), operation)
        }
    }

    private fun calculateValue(operation: AvailableOperations, terms: List<String>) : Double {
        return when (operation) {
             AvailableOperations.PRICE_FOR_KG -> {
                1000 / terms[1].toDouble() * terms[0].toDouble()
            }
            AvailableOperations.KNOWING_PRICE_FOR_KG -> {
                terms[1].toDouble() / 1000 * terms[0].toDouble()
            }
            AvailableOperations.COUNT_FOR_1_KG -> {
                1000 / terms[1].toDouble() * terms[0].toDouble()
            }
            AvailableOperations.PRICE_DEFINITE_WEIGHT -> {
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
            name,
            Calendar.getInstance().time
        )
        modelDB.insertFinalValue(finalValue)
    }

    fun getOperations() : List<AvailableOperations> {
        val operations = modelDB.getOperations()
        return operations.keys.toList()
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