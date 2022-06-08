package com.delirium.unitprice.model

import java.util.*

data class FinalValue(
    val id: UUID? = UUID.randomUUID(),
    val xValue: Long?,
    val yValue: Long?,
    val additionValue: Long? = null,
    val result: Long?,
    val operation: String?,
    var finalString: String? = null,
    var name: String?,
    val date: Date?
)