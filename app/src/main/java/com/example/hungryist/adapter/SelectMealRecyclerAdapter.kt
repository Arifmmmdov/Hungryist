package com.example.hungryist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.hungryist.databinding.ItemSelectMealBinding
import com.example.hungryist.model.MealModel

class SelectMealRecyclerAdapter(
    val mealFullList: List<String>,
    val callback: ((String) -> Unit),
) : RecyclerView.Adapter<SelectMealRecyclerAdapter.MViewHolder>(), Filterable {

    var filteredList = mealFullList.take(5).toMutableList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MViewHolder {
        val binding =
            ItemSelectMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MViewHolder(binding)
    }

    override fun getItemCount(): Int = filteredList.size

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        holder.bind(filteredList[position])
        holder.clickListener(position)
    }

    inner class MViewHolder(val binding: ItemSelectMealBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.txtMeal.text = item
        }

        fun clickListener(position: Int) {
            binding.mainFrame.setOnClickListener {
                callback(filteredList[position])
            }
        }

    }

    override fun getFilter(): Filter {
        return filterValue
    }

    private val filterValue = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filterResults =
                if (p0 == null)
                    mealFullList.take(5)
                else
                    mealFullList.filter {
                        it.contains(p0.toString())
                    }

            val results = FilterResults()
            results.values = filterResults
            return results
        }

        override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
            filteredList.clear()
            val filterResult = filterResults?.values as MutableList<String>
            filteredList.addAll(filterResult.take(5))
            notifyDataSetChanged()
        }

    }
}