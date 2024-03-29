package com.delirium.unitprice.model

import com.delirium.unitprice.AvailableOperations
import com.delirium.unitprice.CallbackDB
import com.delirium.unitprice.RealmConfiguration
import io.realm.Realm
import java.text.SimpleDateFormat
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

    fun deleteFinalValue(idCards: UUID) {
        val finalValue = getFinalValue(idCards)

        finalValue?.let {
            val finalValueDB = converterToDBObject(it)
            realmDB.beginTransaction()
            val removeObject = realmDB.where(FinalValueDB::class.java)
                .equalTo("id", finalValueDB.id)
                .findFirst()
            removeObject?.deleteFromRealm()
            realmDB.commitTransaction()
        } ?: callback.failed()
    }

    fun changeHierarchy(newList: List<FinalValue>) {
        val finalValueDB = newList.map { converterToDBObject(it) }
        realmDB.beginTransaction()
        realmDB.deleteAll()
        finalValueDB.forEach {
            realmDB.copyToRealm(it)
        }
        realmDB.commitTransaction()
    }

    fun changeCalculateValue(finalValue: FinalValue) {
        val changeFinalValue = converterToDBObject(finalValue)
        realmDB.beginTransaction()
        val changeObject = realmDB.where(FinalValueDB::class.java)
            .equalTo("id", changeFinalValue.id)
            .findFirst()
        changeObject?.xValue = finalValue.xValue
        changeObject?.yValue = finalValue.yValue
        if(finalValue.zValue != null) {
            changeObject?.zValue = finalValue.zValue
        }
        changeObject?.result = finalValue.result
        changeObject?.finalString = finalValue.finalString
        realmDB.commitTransaction()
    }

    fun getAllFinalValue() {
        val allValue = realmDB.where(FinalValueDB::class.java).findAll().map {
            converterToObject(it)
        }
        callback.successfulGetAll(allValue)
    }

    fun getFinalValue(id: UUID) : FinalValue? {
        val allValue = realmDB.where(FinalValueDB::class.java).findAll().map {
            converterToObject(it)
        }
        for (item in allValue) {
            if (item.id == id) {
                return item
            }
        }
        return null
    }

    private fun converterToDBObject(finalValue: FinalValue) : FinalValueDB {
        return FinalValueDB(
            id = finalValue.id.toString(),
            xValue = finalValue.xValue,
            yValue = finalValue.yValue,
            zValue = finalValue.zValue,
            additionValue = finalValue.additionValue,
            result = finalValue.result,
            operation = finalValue.operation,
            finalString = finalValue.finalString,
            name = finalValue.name,
            date = finalValue.date?.let {
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(it)
            }
        )
    }

    private fun converterToObject(finalValueDB: FinalValueDB) : FinalValue {
        return FinalValue(
            id = UUID.fromString(finalValueDB.id),
            xValue = finalValueDB.xValue,
            yValue = finalValueDB.yValue,
            zValue = finalValueDB.zValue,
            additionValue = finalValueDB.additionValue,
            result = finalValueDB.result,
            operation = finalValueDB.operation,
            finalString = finalValueDB.finalString,
            name = finalValueDB.name,
            date = finalValueDB.date?.let {
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(it)
            }
        )
    }

    /*** Standard operation ***/
    fun getOperations() : Map<AvailableOperations, String> {
        return availableOperations
    }
}