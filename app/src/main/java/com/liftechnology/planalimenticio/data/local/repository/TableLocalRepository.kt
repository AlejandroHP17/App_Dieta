package com.liftechnology.planalimenticio.data.local.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.model.dataclass.TypeMeals
import com.liftechnology.planalimenticio.ui.utils.Constants.NAME_JSON
import java.io.File

class TableLocalRepository {

    fun getFirstTable(context: Context):
            MutableList<TypeMeals> {
        val list = context.resources.getStringArray(R.array.table_category)
        val gson = Gson()
        val file = File(context.filesDir, NAME_JSON)

        if(file.exists()) {
            val jsonString = file.readText()
            val listType = object : TypeToken<List<TypeMeals>>() {}.type
            val typeMealsList: List<TypeMeals> = gson.fromJson(jsonString, listType)
            return typeMealsList.toMutableList()
        } else {
            val listAdapter = mutableListOf<TypeMeals>()
            list.forEach {
                listAdapter.add(
                    TypeMeals(
                        category = Pair(it, 0),
                        meal1 = Pair("C1", 0),
                        meal2 = Pair("C2", 0),
                        meal3 = Pair("C3", 0),
                        meal4 = Pair("C4", 0),
                        meal5 = Pair("C5", 0),
                        meal6 = Pair("C6", 0),
                        meal7 = Pair("C7", 0)
                    )
                )
            }

            val jsonString = gson.toJson(listAdapter)
            val file = File(context.filesDir, NAME_JSON)
            file.writeText(jsonString)

            return listAdapter
        }


    }




}