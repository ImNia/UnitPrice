package com.delirium.unitprice.calculate

import android.util.Log
import androidx.lifecycle.ViewModel

class CalculatePresenter : ViewModel() {
    private var viewCalculate: CalculateFragment? = null

    private var currentOperation: String? = null

    fun attachView(viewCalculate: CalculateFragment) {
        this.viewCalculate = viewCalculate
    }

    fun detachView() {
        viewCalculate = null
    }

    fun drawCurrentOperation(operation: String? = null) {
        viewCalculate?.drawView(operation ?: "Price for kg")
    }

    fun switchOperation(operation: String) {
        Log.i("PRESENTER", operation)
        currentOperation = operation
        drawCurrentOperation(operation)
    }
}