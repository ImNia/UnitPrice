package com.delirium.unitprice.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
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
) : Parcelable