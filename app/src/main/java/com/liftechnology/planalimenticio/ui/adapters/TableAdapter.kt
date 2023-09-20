package com.liftechnology.planalimenticio.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.liftechnology.planalimenticio.databinding.CardPrincipalTableBinding
import com.liftechnology.planalimenticio.model.dataclass.TypeMeals
import com.liftechnology.planalimenticio.ui.utils.TableNumberMeal
import com.liftechnology.planalimenticio.ui.viewextensions.concatTextWithAdditional

class TableAdapter(
    private val listener: SectionClickedListener,
    private var numberOfMeals: Int
) : ListAdapter<TypeMeals, TableAdapter.ViewHolder>(ItemsDiffCallBack) {

    companion object ItemsDiffCallBack : DiffUtil.ItemCallback<TypeMeals>() {
        override fun areItemsTheSame(oldItem: TypeMeals, newItem: TypeMeals) =
            oldItem.category == newItem.category

        override fun areContentsTheSame(oldItem: TypeMeals, newItem: TypeMeals) =
            oldItem == newItem
    }

    class ViewHolder(val binding: CardPrincipalTableBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TypeMeals, action: SectionClickedListener) {
            binding.item = item
            binding.clickListener = action

            // Concatenar el texto y establecerlo en cada TextView
            binding.textFirstMeal.concatTextWithAdditional(item.meal1!!)
            binding.textSecondMeal.concatTextWithAdditional(item.meal2!!)
            binding.textThirdMeal.concatTextWithAdditional(item.meal3!!)
            binding.textFourthMeal.concatTextWithAdditional(item.meal4!!)
            binding.textFifthMeal.concatTextWithAdditional(item.meal5!!)
            binding.textSixthMeal.concatTextWithAdditional(item.meal6!!)
            binding.textSeventhMeal.concatTextWithAdditional(item.meal7!!)

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardPrincipalTableBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, listener)

        showInit(holder.binding)
        holder.binding.apply {
            if (numberOfMeals < 7) {
                textSeventhMeal.visibility = View.GONE
            }
            if (numberOfMeals < 6) {
                textSixthMeal.visibility = View.GONE
            }
            if (numberOfMeals < 5) {
                textFifthMeal.visibility = View.GONE
            }
            if (numberOfMeals < 4) {
                textFourthMeal.visibility = View.GONE
            }
        }
    }

    fun updateMealsCount(newCount: Int) {
        numberOfMeals = newCount
        notifyDataSetChanged()
    }

    private fun showInit(binding: CardPrincipalTableBinding) {
        binding.apply {
            textSeventhMeal.visibility = View.VISIBLE
            textSixthMeal.visibility = View.VISIBLE
            textFifthMeal.visibility = View.VISIBLE
            textFourthMeal.visibility = View.VISIBLE
        }
    }
}

class SectionClickedListener(val listener:(card:TypeMeals, click:String) -> Unit){
    fun onClickCategory(card:TypeMeals) = listener(card, TableNumberMeal.CATEGORY)
    fun onClickMeal1(card:TypeMeals) = listener(card,TableNumberMeal.M1)
    fun onClickMeal2(card:TypeMeals) = listener(card,TableNumberMeal.M2)
    fun onClickMeal3(card:TypeMeals) = listener(card,TableNumberMeal.M3)
    fun onClickMeal4(card:TypeMeals) = listener(card,TableNumberMeal.M4)
    fun onClickMeal5(card:TypeMeals) = listener(card,TableNumberMeal.M5)
    fun onClickMeal6(card:TypeMeals) = listener(card,TableNumberMeal.M6)
    fun onClickMeal7(card:TypeMeals) = listener(card,TableNumberMeal.M7)
}

