package com.delirium.unitprice.calculate

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.delirium.unitprice.R
import com.delirium.unitprice.databinding.CalculateFragmentBinding
import com.delirium.unitprice.model.FinalValue

class CalculateFragment : Fragment() {
    private val calculatePresenter: CalculatePresenter by activityViewModels()
    lateinit var bindingCalculate: CalculateFragmentBinding

    //TODO in model (map??)
    private val availableOperations = listOf("Price for kg", "Knowing price for kg",
            "Count for 1kg", "Price definite weight")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingCalculate = CalculateFragmentBinding.inflate(inflater, container, false)

        return bindingCalculate.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calculatePresenter.attachView(this)
        calculatePresenter.drawCurrentOperation()
        bindingCalculate.popupMenu.setOnClickListener {
            openMenuOperation(it)
        }

        //TODO check that value is correct and exist
        //TODO hide keyboard after calculate
        bindingCalculate.buttonCalculate.setOnClickListener {
            val xValue = bindingCalculate.xValue.text.toString().toDouble()
            val yValue = bindingCalculate.yValue.text.toString().toDouble()
            calculatePresenter.callCalculate(
                bindingCalculate.popupMenu.text.toString(),
                xValue, yValue
            )
        }
    }

    fun drawView(operation: String) {
        bindingCalculate.popupMenu.text = operation
        when (operation) {
            "Price for kg" -> {
                bindingCalculate.firstValueText.text = getString(R.string.firstValuePriceForKg)
                bindingCalculate.secondValueText.text = getString(R.string.secondValuePriceForKg)
            }
            "Knowing price for kg" -> {
                bindingCalculate.firstValueText.text = getString(R.string.firstValueKnowingPriceForKg)
                bindingCalculate.secondValueText.text = getString(R.string.secondValueKnowingPriceForKg)
            }
            "Count for 1kg" -> {
                bindingCalculate.firstValueText.text = getString(R.string.firstValueCountForKg)
                bindingCalculate.secondValueText.text = getString(R.string.secondValueCountForKg)
            }
            "Price definite weight" -> {
                bindingCalculate.firstValueText.text = getString(R.string.firstValuePriceDefiniteWeight)
                bindingCalculate.secondValueText.text = getString(R.string.secondValuePriceDefiniteWeight)
            }
        }
    }

    private fun openMenuOperation(viewForMenu: View?) {
        val popMenu = PopupMenu(activity, viewForMenu)
        for (item in availableOperations) {
            popMenu.menu.add(item)
        }
        val menuInflater = popMenu.menuInflater
        menuInflater.inflate(R.menu.pop_menu_source, popMenu.menu)
        popMenu.show()

        popMenu.setOnMenuItemClickListener { menuItem ->
            calculatePresenter.switchOperation(menuItem.title.toString())
            true
        }
    }
}