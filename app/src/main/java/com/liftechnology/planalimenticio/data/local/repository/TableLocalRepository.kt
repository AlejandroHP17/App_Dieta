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

    /** Inicia la tabla cuando abre la primera vez
     * @author pelkidev
     * @date 20/09/2023
     * param [context] contexto de donde es construido
     * param [typeTable] tabla de tipo TypeTable
     * */
    fun startTable(context: Context, typeTable: TypeTable): TypeTable {
        /* Declara variables auxiliares */
        val file = File(context.filesDir, NAME_JSON)

        /* Pregunta si el archivo existe */
        return if (file.exists()) {
            // Obtiene la tabla
            getTable(context)
        } else {
            val listAdapter = mutableListOf<TypeMeals>()
            // Limpia la tabla
            val newData = cleanTable(context, typeTable.meals!!)
            // Llena la lista con la tabla limpia
            newData.list.forEach { listAdapter.add(it) }
            // Llena el formacto correcto para actualizar
            val typeTableReturn = TypeTable(listAdapter, typeTable.meals)
            updateTable(context, typeTableReturn)
        }
    }

    /** Actualiza la tabla
     * @author pelkidev
     * @date 20/09/2023
     * param [context] contexto de donde es construido
     * param [typeTable] tabla de tipo TypeTable
     * */
    fun updateTable(context: Context, typeTable: TypeTable): TypeTable {
        /* Declara variables auxiliares */
        val file = File(context.filesDir, NAME_JSON)
        val listTable = typeTable.list
        val listAdapter = mutableListOf<TypeMeals>()
        // Llena la lista con datos enviados
        listTable.forEach { listAdapter.add(it) }
        /* Convierte a formato, escribe el json ye regresa la tabla */
        val typeTableReturn = TypeTable(listAdapter, typeTable.meals)
        val jsonString = gson.toJson(typeTableReturn)
        file.writeText(jsonString)
        return typeTableReturn
    }

    /** Obtiene la tabla
     * @author pelkidev
     * @date 20/09/2023
     * param [context] contexto de donde es construido
     * */
    fun getTable(context: Context): TypeTable {
        val file = File(context.filesDir, NAME_JSON)
        val jsonString = file.readText()
        val listType = object : TypeToken<TypeTable>() {}.type
        return gson.fromJson(jsonString, listType)
    }

    /** Limpia la tabla y pasa a valores default
     * @author pelkidev
     * @date 20/09/2023
     * * param [context] contexto de donde es construido
     * param [title] solo el titulo que no pertence al recycler
     * */
    fun cleanTable(context: Context, title: Pair<String, Int>): TypeTable {
        /* declara variables auxiliares */
        val list = context.resources.getStringArray(R.array.table_category)
        val listAdapter = mutableListOf<TypeMeals>()

        /* Construye con datos base */
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

        /* Convierte a formato, escribe el json ye regresa la tabla */
        val typeTableReturn = TypeTable(listAdapter, title)
        val jsonString = gson.toJson(typeTableReturn)
        val file = File(context.filesDir, NAME_JSON)
        file.writeText(jsonString)

        val listType = object : TypeToken<TypeTable>() {}.type
        return gson.fromJson(jsonString, listType)
    }
}