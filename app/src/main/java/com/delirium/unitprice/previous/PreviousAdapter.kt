package com.delirium.unitprice.previous

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delirium.unitprice.R
import com.delirium.unitprice.databinding.ResultItemBinding
import com.delirium.unitprice.model.FinalValue
import java.text.SimpleDateFormat
import java.util.*

class PreviousAdapter()
    : RecyclerView.Adapter<PreviousAdapter.ViewHolder>() {

    var dataSet: List<FinalValue> = listOf()

    class ViewHolder(var binding: ResultItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentValue: FinalValue) {
            binding.nameInCard.text = currentValue.name
            binding.resultValue.text = currentValue.finalString
            binding.dataCreateResult.text = currentValue.date?.let {
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(it)
            }
            binding.deleteIndicator.setImageResource(R.drawable.ic_delete_black_24dp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ResultItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentValue = dataSet[position]
        holder.bind(currentValue)
    }

    override fun getItemCount() : Int {
        return dataSet.size
    }
}