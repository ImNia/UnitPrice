package com.delirium.unitprice.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.delirium.unitprice.AvailableOperations
import com.delirium.unitprice.R
import com.delirium.unitprice.databinding.FragmentDescriptionBinding

class DescriptionFragment : Fragment() {
    lateinit var bindingDescription: FragmentDescriptionBinding

    private val args by navArgs<DescriptionFragmentArgs>()
    private val itemDesc by lazy { args.itemDesc }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingDescription = FragmentDescriptionBinding.inflate(inflater, container, false)

        drawItem()
        return bindingDescription.root
    }

    fun drawItem() {
        val operation = converterOperationToPresenter(itemDesc.operation!!)
        bindingDescription.operationDesc.text = converterOperationToView(operation)
        when (operation) {
            AvailableOperations.PRICE_FOR_KG -> {
                bindingDescription.firstValueTextDesc.text = getString(R.string.firstValuePriceForKg)
                bindingDescription.xValueDesc.setText(itemDesc.xValue.toString())
                bindingDescription.secondValueTextDesc.text = getString(R.string.secondValuePriceForKg)
                bindingDescription.yValueDesc.setText(itemDesc.yValue.toString())
                bindingDescription.thirdValueTextDesc.visibility = View.INVISIBLE
                bindingDescription.zValueDesc.visibility = View.INVISIBLE
            }
            AvailableOperations.KNOWING_PRICE_FOR_KG -> {
                bindingDescription.firstValueTextDesc.text =
                    getString(R.string.firstValueKnowingPriceForKg)
                bindingDescription.xValueDesc.setText(itemDesc.xValue.toString())
                bindingDescription.secondValueTextDesc.text =
                    getString(R.string.secondValueKnowingPriceForKg)
                bindingDescription.yValueDesc.setText(itemDesc.yValue.toString())
                bindingDescription.thirdValueTextDesc.visibility = View.INVISIBLE
                bindingDescription.zValueDesc.visibility = View.INVISIBLE
            }
            AvailableOperations.COUNT_FOR_1_KG -> {
                bindingDescription.firstValueTextDesc.text = getString(R.string.firstValueCountForKg)
                bindingDescription.xValueDesc.setText(itemDesc.xValue.toString())
                bindingDescription.secondValueTextDesc.text = getString(R.string.secondValueCountForKg)
                bindingDescription.yValueDesc.setText(itemDesc.yValue.toString())
                bindingDescription.thirdValueTextDesc.visibility = View.INVISIBLE
                bindingDescription.zValueDesc.visibility = View.INVISIBLE
            }
            AvailableOperations.PRICE_DEFINITE_WEIGHT -> {
                bindingDescription.firstValueTextDesc.text =
                    getString(R.string.firstValuePriceDefiniteWeight)
                bindingDescription.xValueDesc.setText(itemDesc.xValue.toString())
                bindingDescription.secondValueTextDesc.text =
                    getString(R.string.thirdValuePriceDefiniteWeight)
                bindingDescription.yValueDesc.setText(itemDesc.yValue.toString())
                bindingDescription.thirdValueTextDesc.visibility = View.VISIBLE
                bindingDescription.zValueDesc.visibility = View.VISIBLE
                bindingDescription.zValueDesc.setText("Mda")
                bindingDescription.thirdValueTextDesc.text =
                    getString(R.string.secondValuePriceDefiniteWeight)
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
            else -> {
                "Problem"
            }
        }
    }
}