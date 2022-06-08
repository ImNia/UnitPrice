package com.delirium.unitprice.calculate

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.delirium.unitprice.R
import com.delirium.unitprice.databinding.CalculateFragmentBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class CalculateFragment : Fragment() {
    private val calculatePresenter: CalculatePresenter by activityViewModels()
    lateinit var bindingCalculate: CalculateFragmentBinding

    private var snackBar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingCalculate = CalculateFragmentBinding.inflate(inflater, container, false)

        val fab = activity?.findViewById<FloatingActionButton>(R.id.buttonAppBar)
        fab?.hide()

        bindingCalculate.nameInCalculate.visibility = View.INVISIBLE
        bindingCalculate.buttonSave.visibility = View.INVISIBLE
        return bindingCalculate.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calculatePresenter.attachView(this)

        bindingCalculate.popupMenu.setOnClickListener {
            openMenuOperation(it)
        }

        bindingCalculate.buttonCalculate.setOnClickListener {
            val xValue = bindingCalculate.xValue.text.toString()
            val yValue = bindingCalculate.yValue.text.toString()
            val zValue = bindingCalculate.zValue.text.toString()
            calculatePresenter.callCalculate(
                bindingCalculate.popupMenu.text.toString(),
                xValue, yValue, zValue
            )
            activity?.currentFocus?.let {
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        bindingCalculate.buttonSave.setOnClickListener {
            val nameForSave = if (bindingCalculate.nameInCalculate.text.toString() == "")
                getString(R.string.untitled)
            else bindingCalculate.nameInCalculate.text.toString()
            calculatePresenter.saveResult(
                bindingCalculate.popupMenu.text.toString(),
                bindingCalculate.resultValueInCalculate.text.toString(),
                bindingCalculate.xValue.text.toString(),
                bindingCalculate.yValue.text.toString(),
                nameForSave
            )
            bindingCalculate.root.findNavController().navigate(
                R.id.action_newCalculation_to_previousPrice
            )
        }
    }

    fun drawView(operation: String) {
        bindingCalculate.popupMenu.text = operation
        when (operation) {
            "Price for kg" -> {
                bindingCalculate.firstValueText.text = getString(R.string.firstValuePriceForKg)
                bindingCalculate.secondValueText.text = getString(R.string.secondValuePriceForKg)
                bindingCalculate.thirdValueText.visibility = View.INVISIBLE
                bindingCalculate.zValue.visibility = View.INVISIBLE
            }
            "Knowing price for kg" -> {
                bindingCalculate.firstValueText.text = getString(R.string.firstValueKnowingPriceForKg)
                bindingCalculate.secondValueText.text = getString(R.string.secondValueKnowingPriceForKg)
                bindingCalculate.thirdValueText.visibility = View.INVISIBLE
                bindingCalculate.zValue.visibility = View.INVISIBLE
            }
            "Count for 1kg" -> {
                bindingCalculate.firstValueText.text = getString(R.string.firstValueCountForKg)
                bindingCalculate.secondValueText.text = getString(R.string.secondValueCountForKg)
                bindingCalculate.thirdValueText.visibility = View.INVISIBLE
                bindingCalculate.zValue.visibility = View.INVISIBLE
            }
            "Price definite weight" -> {
                bindingCalculate.firstValueText.text = getString(R.string.firstValuePriceDefiniteWeight)
                bindingCalculate.secondValueText.text = getString(R.string.thirdValuePriceDefiniteWeight)
                bindingCalculate.thirdValueText.visibility = View.VISIBLE
                bindingCalculate.zValue.visibility = View.VISIBLE
                bindingCalculate.thirdValueText.text = getString(R.string.secondValuePriceDefiniteWeight)
            }
        }
    }

    fun drawResultCalculate(result: String, operation: String) {
        val resultString = when (operation) {
            "Price for kg" -> {
                getString(R.string.finalPriceForKg, result)
            }
            "Knowing price for kg" -> {
                getString(R.string.finalKnowingPriceForKg, result,
                    bindingCalculate.xValue.text.toString()
                )
            }
            "Count for 1kg" -> {
                getString(R.string.finalCountForKg, result,
                    bindingCalculate.xValue.text.toString()
                )
            }
            "Price definite weight" -> {
                getString(R.string.finalPriceDefiniteWeight,
                    bindingCalculate.xValue.text.toString(),
                    bindingCalculate.yValue.text.toString(),
                    bindingCalculate.zValue.text.toString(),
                    result
                )
            }
            else -> {
                "Problem"
            }
        }
        bindingCalculate.resultValueInCalculate.text = resultString
        bindingCalculate.nameInCalculate.visibility = View.VISIBLE
        bindingCalculate.buttonSave.visibility = View.VISIBLE
    }

    private fun openMenuOperation(viewForMenu: View?) {
        val operations = calculatePresenter.getOperations()
        val popMenu = PopupMenu(activity, viewForMenu)
        for (item in operations) {
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

    /**** SnackBar ****/
    fun snackBarWithError() {
        val textError = getString(R.string.not_filled_error)
        snackBar = Snackbar
            .make(bindingCalculate.linearLayout, textError, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.ok_error) {
                hideSnackBar()
            }
        snackBar?.show()
    }

    fun hideSnackBar() {
        snackBar?.let {
            if (it.isShown) it.dismiss()
        }
    }
}