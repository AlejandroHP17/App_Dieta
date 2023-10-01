package com.liftechnology.planalimenticio.data.local.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.liftechnology.planalimenticio.model.dataclass.ListTypeTable
import com.liftechnology.planalimenticio.model.dataclass.TypeTable
import com.liftechnology.planalimenticio.ui.utils.Constants
import java.io.File

class ListTableLocalRepository {
    // Variable para trabajar json
    private val gson = Gson()

    fun saveListTable(context: Context, name: String, table: TypeTable) {
        // Primero, verifica si el archivo existe y lee su contenido si es necesario
        val file = File(context.filesDir, Constants.NAME_JSON_LIST_TABLE)
        val existingData = if (file.exists()) {
            val jsonString = file.readText()
            val listType = object : TypeToken<List<ListTypeTable>>() {}.type
            gson.fromJson<List<ListTypeTable>>(jsonString, listType)
        } else {
            null
        }

        // Luego, actualiza la lista de datos
        val newData = mutableListOf<ListTypeTable>()

        if (existingData != null) {
            // Si ya existe, agrega los datos existentes
            newData.addAll(existingData)
        }

        // Agrega los nuevos datos a la lista
        newData.add(ListTypeTable(table, name))

        // Finalmente, guarda la lista actualizada en el archivo
        val jsonString = gson.toJson(newData)
        file.writeText(jsonString)
    }

    fun getListTableDiets(context: Context):List<ListTypeTable>?{
        val file = File(context.filesDir, Constants.NAME_JSON_LIST_TABLE)
        val existingData = if (file.exists()) {
            val jsonString = file.readText()
            val listType = object : TypeToken<List<ListTypeTable>>() {}.type
            gson.fromJson(jsonString, listType)}
        else{ null}
        return existingData
    }

    fun deleteItemTableDiet(context: Context, position: Int): List<ListTypeTable>? {
        val file = File(context.filesDir, Constants.NAME_JSON_LIST_TABLE)
        val table = getListTableDiets(context)
        return if (table.isNullOrEmpty()){
            null
        }else{
            val mutableList = table.toMutableList()
            mutableList.removeAt(position)
            val jsonString = gson.toJson(mutableList)
            file.writeText(jsonString)
            mutableList
        }

    }
}