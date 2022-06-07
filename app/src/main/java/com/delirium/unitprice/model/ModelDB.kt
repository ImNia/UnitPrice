package com.delirium.unitprice.model

import com.delirium.unitprice.AvailableOperations
import com.delirium.unitprice.CallbackDB
import com.delirium.unitprice.RealmConfiguration
import io.realm.Realm
import java.util.*

class ModelDB(private val callback: CallbackDB) {
    private val availableOperations = mapOf(
        AvailableOperations.PRICE_FOR_KG to "Price for kg",
        AvailableOperations.KNOWING_PRICE_FOR_KG to "Knowing price for kg",
        AvailableOperations.COUNT_FOR_1_KG to "Count for 1kg",
        AvailableOperations.PRICE_DEFINITE_WEIGHT to "Price definite weight"
    )

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
            operation = finalValue.operation,
            finalString = finalValue.finalString,
            name = finalValue.name
        )
    }

    private fun converterToObject(finalValueDB: FinalValueDB) : FinalValue {
        return FinalValue(
            id = UUID.fromString(finalValueDB.id),
            xValue = finalValueDB.xValue,
            yValue = finalValueDB.yValue,
            additionValue = finalValueDB.additionValue,
            result = finalValueDB.result,
            operation = finalValueDB.operation,
            finalString = finalValueDB.finalString,
            name = finalValueDB.name
        )
    }

    /*** Standard operation ***/
    fun getOperations() : Map<AvailableOperations, String> {
        return availableOperations
    }
}