package com.liftechnology.planalimenticio.ui.adapters

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.liftechnology.planalimenticio.data.network.models.response.CategoryResponse
import com.liftechnology.planalimenticio.databinding.CardCategoriesBinding

class CategoriesAdapter(private val listener: CategoriesClickedListener):
    ListAdapter<CategoryResponse, CategoriesAdapter.ViewHolder>(ItemsDiffCallBack){

    /** Use the [ItemsDiffCallBack] to detect if any item is duplicated and then no return the value */
    companion object ItemsDiffCallBack : DiffUtil.ItemCallback<CategoryResponse>() {
        override fun areItemsTheSame(oldItem: CategoryResponse, newItem: CategoryResponse) =
            oldItem.category == newItem.category

        override fun areContentsTheSame(oldItem: CategoryResponse, newItem: CategoryResponse) =
            oldItem == newItem
    }

    /** The class [ViewHolder] build the view*/
    class ViewHolder(private val binding : CardCategoriesBinding): RecyclerView.ViewHolder(binding.root){
        // Method like a listener; bring the item and the action of click
        fun bind(item:CategoryResponse, action:CategoriesClickedListener){
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(CardCategoriesBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, listener)
    }

}

class CategoriesClickedListener(val listener: (item: CategoryResponse) -> Unit) {
    // The listener of the click
    fun onClick(item: CategoryResponse) = listener(item)
}