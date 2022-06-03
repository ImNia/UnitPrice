package com.delirium.unitprice.model

import com.delirium.unitprice.CallbackDB
import com.delirium.unitprice.RealmConfiguration
import io.realm.Realm
import java.util.*

class ModelDB(private val callback: CallbackDB) {
    private val configDB: RealmConfiguration = RealmConfiguration()
    val realmDB: Realm = Realm.getInstance(configDB.getConfigDB())

    fun insertFinalValue(finalValue: FinalValue) {
        realmDB.beginTransaction()
        realmDB.copyToRealm(converterToDBObject(finalValue))
        realmDB.commitTransaction()
    }

    fun deleteFinalValue(finalValue: FinalValue) {
        realmDB.beginTransaction()
        val removeObject = realmDB.where(FinalValueDB::class.java)
            .equalTo("id", finalValue.id)
            .findFirst()
        removeObject?.deleteFromRealm()
        realmDB.commitTransaction()
    }

    fun getAllFinalValue() {
        val allValue = realmDB.where(FinalValueDB::class.java).findAll().map {
            converterToObject(it)
        }
        callback.successfulGetAll(allValue)
    }

    private fun converterToDBObject(finalValue: FinalValue) : FinalValueDB {
        return FinalValueDB(
            id = finalValue.id.toString(),
            xValue = finalValue.xValue,
            yValue = finalValue.yValue,
            additionValue = finalValue.additionValue,
            result = finalValue.result,
            operation = finalValue.operation
        )
    }

    private fun converterToObject(finalValueDB: FinalValueDB) : FinalValue {
        return FinalValue(
            id = UUID.fromString(finalValueDB.id),
            xValue = finalValueDB.xValue,
            yValue = finalValueDB.yValue,
            additionValue = finalValueDB.additionValue,
            result = finalValueDB.result,
            operation = finalValueDB.operation
        )
    }
}