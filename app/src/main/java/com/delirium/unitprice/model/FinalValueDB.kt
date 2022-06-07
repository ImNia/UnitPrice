package com.delirium.unitprice.model

import io.realm.RealmObject

open class FinalValueDB(
    var id: String? = null,
    var xValue: Long? = null,
    var yValue: Long? = null,
    var additionValue: Long? = null,
    var result: Long? = null,
    var operation: String? = null,
    var finalString: String? = null,
    var name: String? = null
) : RealmObject()