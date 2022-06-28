package com.delirium.unitprice.previous

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delirium.unitprice.R
import com.delirium.unitprice.databinding.ResultItemBinding
import com.delirium.unitprice.model.FinalValue
import java.text.SimpleDateFormat
import java.util.*

class PreviousAdapter(
    private val clickListener: ClickResultItem
) : RecyclerView.Adapter<PreviousAdapter.ViewHolder>(), ItemTouchHelperAdapter{

    var dataSet: MutableList<FinalValue> = mutableListOf()

    class ViewHolder(var binding: ResultItemBinding)
        : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var clickEvent: ClickResultItem
        fun bind(currentValue: FinalValue, clickListener: ClickResultItem) {
            binding.nameInCard.text = currentValue.name
            binding.resultValue.text = currentValue.finalString
            binding.dataCreateResult.text = currentValue.date?.let {
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(it)
            }
            binding.deleteIndicator.setImageResource(R.drawable.ic_delete_black_24dp)

            binding.deleteIndicator.isClickable = true
            binding.deleteIndicator.setOnClickListener(this)
//            binding.itemResult.isClickable = true
//            binding.itemResult.setOnLongClickListener(this)

            binding.idCards.text = currentValue.id.toString()
            binding.idCards.visibility = View.INVISIBLE
            clickEvent = clickListener
        }

        override fun onClick(p0: View?) {
            clickEvent.clickOnResult(
                binding.idCards.text.toString(),
                binding.deleteIndicator.id == p0?.id
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ResultItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentValue = dataSet[position]
        holder.bind(currentValue, clickListener)
    }

    override fun getItemCount() : Int {
        return dataSet.size
    }

    override fun onItemDismiss(position: Int) {
        val card = dataSet.get(position)
        dataSet.removeAt(position)
        clickListener.deleteCard(card)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(dataSet, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(dataSet, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        clickListener.changePlaceCards(dataSet)
    }
}

interface ClickResultItem {
    //TODO how to do better
    fun clickOnResult(idCard: String, isDelete: Boolean)
    fun changePlaceCards(newList: List<FinalValue>)
    fun deleteCard(finalValue: FinalValue)
}