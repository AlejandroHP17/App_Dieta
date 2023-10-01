package com.liftechnology.planalimenticio.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.liftechnology.planalimenticio.databinding.CardListDietBinding
import com.liftechnology.planalimenticio.model.dataclass.ListTypeTable

class GeneratorAdapter(private val listener: GeneratorClickedListener) :
    ListAdapter<ListTypeTable, GeneratorAdapter.ViewHolder>(ItemsDiffCallBack) {

    companion object ItemsDiffCallBack : DiffUtil.ItemCallback<ListTypeTable>() {
        override fun areItemsTheSame(oldItem: ListTypeTable, newItem: ListTypeTable) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: ListTypeTable, newItem: ListTypeTable) =
            oldItem == newItem
    }

    class ViewHolder(private val binding: CardListDietBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: ListTypeTable, position: Int, action: GeneratorClickedListener) {
            binding.item = item

            // Give the action to the click
            binding.clickListener = action
            // Pass the position to the click listener
            binding.linearText.setOnClickListener {
                action.onClickCard(item, position)
            }

            binding.linearOptions.setOnClickListener {
                action.onClickOptions(item, position)
            }


            binding.executePendingBindings()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardListDietBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, position, listener)
    }
}


class GeneratorClickedListener(val listener: (item: ListTypeTable, click:String, position:Int) -> Unit) {
    // The listener of the click
    fun onClickCard(item: ListTypeTable, position: Int) = listener(item, "Data", position)
    fun onClickOptions(item: ListTypeTable, position: Int) = listener(item, "Delete", position)
}