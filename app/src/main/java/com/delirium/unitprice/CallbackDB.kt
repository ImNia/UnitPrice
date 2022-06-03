package com.delirium.unitprice

import com.delirium.unitprice.model.FinalValue

interface CallbackDB {
    fun successful()
    fun failed()
    fun successfulGetAll(allData: List<FinalValue>)
}