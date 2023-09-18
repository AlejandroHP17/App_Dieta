package com.liftechnology.planalimenticio.data.local.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.model.dataclass.TypeMeals
import com.liftechnology.planalimenticio.model.dataclass.TypeTable
import com.liftechnology.planalimenticio.ui.utils.Constants.NAME_JSON
import java.io.File

class TableLocalRepository {
    // Variable para trabajar json
    private val gson = Gson()


    fun readTable(context: Context, typeTable: TypeTable): TypeTable {
        /* declara variables auxiliares */
        val list = context.resources.getStringArray(R.array.table_category)
        val file = File(context.filesDir, NAME_JSON)
        val listTable = typeTable.list

        /* Pregunta si el archivo existe */
        if (file.exists()) {
            /* Lee el archivo, convierte al modelo TypeTable y regresa la información guardada */
            val jsonString = file.readText()
            val listType = object : TypeToken<TypeTable>() {}.type
            return gson.fromJson(jsonString, listType)
        } else {
            // Crea una lista adecuada para agregar datos
            val listAdapter = mutableListOf<TypeMeals>()

            /* Pregunta si la lista viene vacia
            *  Si es vacia indica que entra por iniciar la app
            *  Si trae datos significa que navegó
            * */
            if (listTable.isNullOrEmpty()) {
                /* Construye con datos base */
                list.forEach {
                    listAdapter.add(
                        TypeMeals(
                            category = Pair(it, 3),
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
            } else {
                /* Construye con datos enviados */
                listTable.forEach {
                    listAdapter.add(it)
                }
            }
            val typeTableReturn = TypeTable(listAdapter, typeTable.meals)
            val jsonString = gson.toJson(typeTableReturn)
            val file = File(context.filesDir, NAME_JSON)
            file.writeText(jsonString)
            return typeTableReturn
        }
    }

    fun updateTable(context: Context, typeTable: TypeTable) {
        val file = File(context.filesDir, NAME_JSON)
        val listTable = typeTable.list

        val listAdapter = mutableListOf<TypeMeals>()
        listTable.forEach {
            listAdapter.add(it)
        }

        val typeTableReturn = TypeTable(listAdapter, typeTable.meals)
        val jsonString = gson.toJson(typeTableReturn)
        file.writeText(jsonString)
    }
}