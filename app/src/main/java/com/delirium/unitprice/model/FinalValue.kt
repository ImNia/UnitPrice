package com.delirium.unitprice.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class FinalValue(
    val id: UUID? = UUID.randomUUID(),
    var xValue: Long?,
    var yValue: Long?,
    var zValue: Long?,
    val additionValue: Long? = null,
    var result: Long?,
    val operation: String?,
    var finalString: String? = null,
    var name: String?,
    val date: Date?
) : Parcelable