package com.delirium.unitprice.previous

import android.util.Log
import android.view.LayoutInflater
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
        : RecyclerView.ViewHolder(binding.root){

        private lateinit var clickEvent: ClickResultItem
        fun bind(currentValue: FinalValue, clickListener: ClickResultItem) {
            binding.nameInCard.text = currentValue.name
            binding.resultValue.text = currentValue.finalString
            binding.dataCreateResult.text = currentValue.date?.let {
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(it)
            }
            binding.deleteIndicator.setImageResource(R.drawable.ic_delete_black_24dp)

//            binding.itemResult.isClickable = true
//            binding.itemResult.setOnLongClickListener(this)
            clickEvent = clickListener
        }

/*        override fun onLongClick(p0: View?): Boolean {
            Log.i("HOLD", "onLongClick")
//            clickEvent.clickOnResult()
            return true
        }*/

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
        dataSet.removeAt(position)
        notifyItemRemoved(position)
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
    }
}

interface ClickResultItem {
    fun clickOnResult()
}