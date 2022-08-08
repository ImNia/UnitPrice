package com.delirium.unitprice.description

import androidx.lifecycle.ViewModel
import com.delirium.unitprice.AvailableOperations
import com.delirium.unitprice.CallbackDB
import com.delirium.unitprice.model.FinalValue
import com.delirium.unitprice.model.ModelDB
import java.util.*

class DescriptionPresenter : ViewModel(), CallbackDB {
    private var viewDescription: DescriptionFragment? = null
    private lateinit var modelDB: ModelDB

    fun attachView(viewDescription: DescriptionFragment) {
        this.viewDescription = viewDescription
        modelDB = ModelDB(this)
    }

    fun changeCalculateValue(
        operation: AvailableOperations,
        firstValue: String?,
        secondValue: String?,
        thirdValue: String?,
        finalValue: FinalValue
    ) {
        val terms = listOf(firstValue, secondValue, thirdValue).filterNotNull()
        val result = calculateValue(operation, terms)
        finalValue.xValue = firstValue?.toLong()
        finalValue.yValue = secondValue?.toLong()
        finalValue.zValue = if(thirdValue != "") thirdValue?.toLong() else null
        finalValue.result = result.toLong()

        finalValue.finalString = viewDescription?.drawResultDescription(finalValue.result.toString(), operation)

        modelDB.changeCalculateValue(finalValue)
        viewDescription?.changeValueItemDesc(finalValue)
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
        }
    }
    
    override fun successful() {
        TODO("Not yet implemented")
    }

    override fun failed() {
        TODO("Not yet implemented")
    }

    override fun successfulGetAll(allData: List<FinalValue>) {
        TODO("Not yet implemented")
    }
}