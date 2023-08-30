package com.liftechnology.planalimenticio.ui.adapters

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.liftechnology.planalimenticio.data.network.models.response.local.ModelCardList
import com.liftechnology.planalimenticio.databinding.CardDetailCategoriesBinding

class GeneralAdapter(private val listener: FoodClickedListener):
    ListAdapter<ModelCardList, GeneralAdapter.ViewHolder>(ItemsDiffCallBack){

    /** Use the [ItemsDiffCallBack] to detect if any item is duplicated and then no return the value */
    companion object ItemsDiffCallBack : DiffUtil.ItemCallback<ModelCardList>() {
        override fun areItemsTheSame(oldItem: ModelCardList, newItem: ModelCardList) =
            oldItem.alimento == newItem.alimento

        override fun areContentsTheSame(oldItem: ModelCardList, newItem: ModelCardList) =
            oldItem == newItem
    }

    class ViewHolder(private val binding : CardDetailCategoriesBinding): RecyclerView.ViewHolder(binding.root){
        // Method like a listener; bring the item and the action of click
        fun bind(item: ModelCardList, action:FoodClickedListener){
            // Synchronize the item response with the view
            binding.item = item

            try {
                val startColor = item.startColor
                val endColor = item.endColor
                val colors =
                    intArrayOf(
                        Color.parseColor(startColor),
                        Color.parseColor(endColor))
                val gradientDrawable = GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT, colors
                )
                gradientDrawable.colors = colors
                // setting the corner radius on gradient drawable

                binding.linearCard.background = gradientDrawable

            } catch (e: Exception) {
                // default color to white

            }

            // Give the action to the click
            binding.clickListener = action
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardDetailCategoriesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, listener)
    }


}

class FoodClickedListener(val listener: (item: ModelCardList) -> Unit){
    fun onClick(item: ModelCardList) = listener(item)
}