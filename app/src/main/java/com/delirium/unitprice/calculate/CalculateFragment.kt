package com.delirium.unitprice.calculate

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.delirium.unitprice.R
import com.delirium.unitprice.databinding.CalculateFragmentBinding
import com.delirium.unitprice.model.FinalValue
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
            calculatePresenter.callCalculate(
                bindingCalculate.popupMenu.text.toString(),
                xValue, yValue
            )
            activity?.currentFocus?.let {
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
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