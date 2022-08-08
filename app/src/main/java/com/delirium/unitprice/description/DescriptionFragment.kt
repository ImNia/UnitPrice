package com.delirium.unitprice.description

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.delirium.unitprice.AvailableOperations
import com.delirium.unitprice.R
import com.delirium.unitprice.calculate.CalculatePresenter
import com.delirium.unitprice.databinding.FragmentDescriptionBinding
import com.delirium.unitprice.databinding.PreviousResultsFragmentBinding
import com.delirium.unitprice.model.FinalValue
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DescriptionFragment : Fragment() {
    lateinit var bindingDescription: FragmentDescriptionBinding
    private val descriptionPresenter: DescriptionPresenter by activityViewModels()
    lateinit var bindingDisplay: PreviousResultsFragmentBinding

    private val args by navArgs<DescriptionFragmentArgs>()
    private val itemDesc by lazy { args.itemDesc }
    private lateinit var itemDescChange: FinalValue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemDescChange = itemDesc
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingDescription = FragmentDescriptionBinding.inflate(inflater, container, false)

        descriptionPresenter.attachView(this)
        drawItem()

        val fab = activity?.findViewById<FloatingActionButton>(R.id.buttonAppBar)
        fab?.hide()

        bindingDescription.buttonCalculateDesc.setOnClickListener {
            val xValue = bindingDescription.xValueDesc.text.toString()
            val yValue = bindingDescription.yValueDesc.text.toString()
            val zValue = bindingDescription.zValueDesc.text.toString()

            descriptionPresenter.changeCalculateValue(
                converterOperationToPresenter(bindingDescription.operationDesc.text.toString()),
                xValue, yValue, zValue, itemDescChange
            )
        }

        setHasOptionsMenu(true)
        val toolbar: MaterialToolbar? = activity?.findViewById(R.id.toolBar)
        toolbar?.menu?.findItem(R.id.menuAdded)?.isVisible = false
        toolbar?.menu?.findItem(R.id.menuUpdate)?.isVisible = false

        toolbar?.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuMore -> {
                    Log.i("PREVIOUS", "Click on MORE")
                    true
                }
                else -> {
                    false
                }
            }
        }

        return bindingDescription.root
    }

    private fun drawItem() {
        val operation = converterOperationToPresenter(itemDescChange.operation!!)
        bindingDescription.operationDesc.text = converterOperationToView(operation)

        bindingDescription.xValueDesc.setText(itemDescChange.xValue.toString())
        bindingDescription.yValueDesc.setText(itemDescChange.yValue.toString())
        bindingDescription.resultValueDesc.text = itemDescChange.finalString
        when (operation) {
            AvailableOperations.PRICE_FOR_KG -> {
                bindingDescription.firstValueTextDesc.text = getString(R.string.firstValuePriceForKg)
                bindingDescription.secondValueTextDesc.text = getString(R.string.secondValuePriceForKg)
                bindingDescription.thirdValueTextDesc.visibility = View.INVISIBLE
                bindingDescription.zValueDesc.visibility = View.INVISIBLE
            }
            AvailableOperations.KNOWING_PRICE_FOR_KG -> {
                bindingDescription.firstValueTextDesc.text =
                    getString(R.string.firstValueKnowingPriceForKg)
                bindingDescription.secondValueTextDesc.text =
                    getString(R.string.secondValueKnowingPriceForKg)
                bindingDescription.thirdValueTextDesc.visibility = View.INVISIBLE
                bindingDescription.zValueDesc.visibility = View.INVISIBLE
            }
            AvailableOperations.COUNT_FOR_1_KG -> {
                bindingDescription.firstValueTextDesc.text = getString(R.string.firstValueCountForKg)
                bindingDescription.secondValueTextDesc.text = getString(R.string.secondValueCountForKg)
                bindingDescription.thirdValueTextDesc.visibility = View.INVISIBLE
                bindingDescription.zValueDesc.visibility = View.INVISIBLE
            }
            AvailableOperations.PRICE_DEFINITE_WEIGHT -> {
                bindingDescription.firstValueTextDesc.text =
                    getString(R.string.firstValuePriceDefiniteWeight)
                bindingDescription.secondValueTextDesc.text =
                    getString(R.string.thirdValuePriceDefiniteWeight)
                bindingDescription.thirdValueTextDesc.visibility = View.VISIBLE
                bindingDescription.zValueDesc.visibility = View.VISIBLE
                bindingDescription.zValueDesc.setText(itemDescChange.zValue.toString())
                bindingDescription.thirdValueTextDesc.text =
                    getString(R.string.secondValuePriceDefiniteWeight)
            }
        }
    }

    fun changeValueItemDesc(changeValue: FinalValue) {
        itemDescChange = changeValue
        drawItem()
    }

    fun drawResultDescription(result: String, operation: AvailableOperations) : String {
        return when (operation) {
            AvailableOperations.PRICE_FOR_KG -> {
                getString(R.string.finalPriceForKg, result)
            }
            AvailableOperations.KNOWING_PRICE_FOR_KG -> {
                getString(
                    R.string.finalKnowingPriceForKg, result,
                    itemDescChange.xValue.toString()
                )
            }
            AvailableOperations.COUNT_FOR_1_KG -> {
                getString(
                    R.string.finalCountForKg, result,
                    itemDescChange.xValue.toString()
                )
            }
            AvailableOperations.PRICE_DEFINITE_WEIGHT -> {
                getString(
                    R.string.finalPriceDefiniteWeight,
                    itemDescChange.xValue.toString(),
                    itemDescChange.yValue.toString(),
                    itemDescChange.zValue.toString(),
                    result
                )
            }
        }
    }

    private fun converterOperationToPresenter(operation: String): AvailableOperations {
        return when (operation) {
            getString(R.string.priceForKg) -> {
                AvailableOperations.PRICE_FOR_KG
            }
            getString(R.string.knowingPriceForKg) -> {
                AvailableOperations.KNOWING_PRICE_FOR_KG
            }
            getString(R.string.countForKg) -> {
                AvailableOperations.COUNT_FOR_1_KG
            }
            getString(R.string.priceDefiniteWeight) -> {
                AvailableOperations.PRICE_DEFINITE_WEIGHT
            }
            else -> {
                AvailableOperations.PRICE_FOR_KG
            }
        }
    }

    private fun converterOperationToView(operation: AvailableOperations): String {
        return when (operation) {
            AvailableOperations.PRICE_FOR_KG -> {
                getString(R.string.priceForKg)
            }
            AvailableOperations.COUNT_FOR_1_KG -> {
                getString(R.string.countForKg)
            }
            AvailableOperations.KNOWING_PRICE_FOR_KG -> {
                getString(R.string.knowingPriceForKg)
            }
            AvailableOperations.PRICE_DEFINITE_WEIGHT -> {
                getString(R.string.priceDefiniteWeight)
            }
        }
    }
}