package com.delirium.unitprice

import com.delirium.unitprice.calculate.CalculatePresenter
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CalculateUnitTest {
    private lateinit var calculatePresenter: CalculatePresenter

    @Before
    fun beforeTest() {
        calculatePresenter = CalculatePresenter()
    }

    @Test
    fun priceForKGOperationTest() {
        val operators = listOf("740", "350")
        val result = calculatePresenter.calculateValue(AvailableOperations.PRICE_FOR_KG, operators)
        Assert.assertEquals(String.format("%.2f", result), "2114,29")
    }

    @Test
    fun knowingPriceForKGOperationTest() {
        val operators = listOf("700", "750")
        val result = calculatePresenter.calculateValue(AvailableOperations.KNOWING_PRICE_FOR_KG, operators)
        Assert.assertEquals(String.format("%.2f", result), "525,00")
    }

    @Test
    fun countForKGOperationTest() {
        val operators = listOf("250", "370")
        val result = calculatePresenter.calculateValue(AvailableOperations.COUNT_FOR_1_KG, operators)
        Assert.assertEquals(String.format("%.2f", result), "675,68")
    }

    @Test
    fun priceDefiniteWeightOperationTest() {
        val operators = listOf("140", "100", "350")
        val result = calculatePresenter.calculateValue(AvailableOperations.PRICE_DEFINITE_WEIGHT, operators)
        Assert.assertEquals(String.format("%.2f", result), "490,00")
    }
}
