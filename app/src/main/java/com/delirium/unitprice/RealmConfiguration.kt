package com.delirium.unitprice

import io.realm.RealmConfiguration

class RealmConfiguration {
    private val config: RealmConfiguration =
        RealmConfiguration.Builder()
            .name("calculateData.realm")
            .schemaVersion(1)
            .build()

    fun getConfigDB() = config
}