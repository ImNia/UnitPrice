package com.delirium.unitprice.model

data class FinalValue(
    val id: Int?,
    val xValue: Long?,
    val yValue: Long?,
    val additionValue: Long? = null,
    val result: Long?,
    val operation: String,
    var finalString: String? = "NULL"
)